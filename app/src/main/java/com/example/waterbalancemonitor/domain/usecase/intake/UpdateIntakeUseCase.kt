package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import javax.inject.Inject

class UpdateIntakeUseCase @Inject constructor(
    private val intakeEventRepository: IntakeEventRepository,
    private val refreshDailySummary: RefreshDailySummaryUseCase
) {
    suspend operator fun invoke(event: IntakeEvent) {
        val previous = intakeEventRepository.getById(event.id)
        intakeEventRepository.save(event)
        previous?.consumedAt?.date?.let { previousDate ->
            if (previousDate != event.consumedAt.date) {
                refreshDailySummary(previousDate)
            }
        }
        refreshDailySummary(event.consumedAt.date)
    }
}
