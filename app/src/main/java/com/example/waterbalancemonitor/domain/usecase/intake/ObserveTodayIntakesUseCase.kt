package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class ObserveTodayIntakesUseCase @Inject constructor(
    private val intakeEventRepository: IntakeEventRepository
) {
    operator fun invoke(): Flow<List<IntakeEvent>> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val start = LocalDateTime(today, LocalTime(0, 0))
        val end = LocalDateTime(today, LocalTime(23, 59, 59, 999_999_999))
        return intakeEventRepository.observeBetween(start, end)
    }
}
