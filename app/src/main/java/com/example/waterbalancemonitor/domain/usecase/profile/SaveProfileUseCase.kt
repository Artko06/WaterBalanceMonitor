package com.example.waterbalancemonitor.domain.usecase.profile

import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import com.example.waterbalancemonitor.domain.usecase.goal.EnsureDailyGoalUseCase
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val ensureDailyGoal: EnsureDailyGoalUseCase
) {
    suspend operator fun invoke(profile: Profile) {
        profileRepository.saveProfile(profile)
        ensureDailyGoal(profile.updatedAt.date, profile.dailyNormMl)
    }
}
