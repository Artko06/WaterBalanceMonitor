package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import com.example.waterbalancemonitor.domain.usecase.achievement.CheckAchievementsUseCase
import javax.inject.Inject

class LogIntakeUseCase @Inject constructor(
    private val intakeEventRepository: IntakeEventRepository,
    private val refreshDailySummary: RefreshDailySummaryUseCase,
    private val checkAchievements: CheckAchievementsUseCase
) {
    suspend operator fun invoke(event: IntakeEvent) {
        intakeEventRepository.save(event)
        refreshDailySummary(event.consumedAt.date)
        checkAchievements()
    }
}
