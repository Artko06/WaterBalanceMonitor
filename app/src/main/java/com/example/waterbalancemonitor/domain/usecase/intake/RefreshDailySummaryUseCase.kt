package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import com.example.waterbalancemonitor.domain.repository.DrinkTypeRepository
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject
import kotlin.math.roundToInt

class RefreshDailySummaryUseCase @Inject constructor(
    private val intakeEventRepository: IntakeEventRepository,
    private val drinkTypeRepository: DrinkTypeRepository,
    private val dailySummaryRepository: DailySummaryRepository,
    private val dailyGoalRepository: DailyGoalRepository
) {

    suspend operator fun invoke(date: LocalDate) {
        val start = LocalDateTime(date, LocalTime(0, 0))
        val end = LocalDateTime(date, LocalTime(23, 59, 59, 999_999_999))
        val events = intakeEventRepository.getBetween(start, end)
        val coefficients = drinkTypeRepository.getAll()
            .associate { it.id to it.hydrationCoefficient }
        val totalMl = events.sumOf { event ->
            val coefficient = coefficients[event.drinkTypeId] ?: 1.0
            (event.volumeMl * coefficient).roundToInt()
        }
        val existing = dailySummaryRepository.getByDate(date)
        dailySummaryRepository.save(
            DailySummary(
                id = existing?.id ?: 0,
                date = date,
                totalMl = totalMl,
                eventsCount = events.size,
                lastEventAt = events.maxOfOrNull { it.consumedAt }
            )
        )
        dailyGoalRepository.getByDate(date)?.let { goal ->
            dailyGoalRepository.setCompleted(date, totalMl >= goal.targetMl)
        }
    }
}
