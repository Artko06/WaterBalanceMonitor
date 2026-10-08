package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class EnsureTodayGoalUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dailyGoalRepository: DailyGoalRepository,
    private val ensureDailyGoal: EnsureDailyGoalUseCase
) {
    suspend operator fun invoke() {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        if (dailyGoalRepository.getByDate(today) != null) return
        val profile = profileRepository.getProfile() ?: return
        ensureDailyGoal(today, profile.dailyNormMl)
    }
}
