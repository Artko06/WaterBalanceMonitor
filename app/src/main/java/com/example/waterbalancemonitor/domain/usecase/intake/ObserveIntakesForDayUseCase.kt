package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class ObserveIntakesForDayUseCase @Inject constructor(
    private val intakeEventRepository: IntakeEventRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<IntakeEvent>> =
        intakeEventRepository.observeBetween(
            LocalDateTime(date, LocalTime(0, 0)),
            LocalDateTime(date, LocalTime(23, 59, 59, 999_999_999))
        )
}
