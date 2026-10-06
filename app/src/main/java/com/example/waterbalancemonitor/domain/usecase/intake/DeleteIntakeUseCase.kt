package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import javax.inject.Inject

class DeleteIntakeUseCase @Inject constructor(
    private val intakeEventRepository: IntakeEventRepository,
    private val refreshDailySummary: RefreshDailySummaryUseCase
) {
    suspend operator fun invoke(event: IntakeEvent) {
        intakeEventRepository.deleteById(event.id)
        refreshDailySummary(event.consumedAt.date)
    }
}
