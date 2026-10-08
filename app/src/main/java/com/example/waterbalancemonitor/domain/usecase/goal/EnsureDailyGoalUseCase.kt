package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class EnsureDailyGoalUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository,
    private val dailySummaryRepository: DailySummaryRepository
) {
    suspend operator fun invoke(date: LocalDate, targetMl: Int) {
        val existing = dailyGoalRepository.getByDate(date)
        val summary = dailySummaryRepository.getByDate(date)
        val isCompleted = summary != null && summary.totalMl >= targetMl
        dailyGoalRepository.save(
            DailyGoal(
                id = existing?.id ?: 0,
                date = date,
                targetMl = targetMl,
                baseNormMl = targetMl,
                bonusMl = 0,
                isCompleted = isCompleted
            )
        )
    }
}
