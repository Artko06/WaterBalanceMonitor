package com.example.waterbalancemonitor.domain.usecase.achievement

import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAchievementsUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository
) {
    operator fun invoke(): Flow<List<Achievement>> = achievementRepository.observeAll()
}
