package com.example.waterbalancemonitor.domain.usecase.achievement

import com.example.waterbalancemonitor.domain.model.UserAchievement
import com.example.waterbalancemonitor.domain.repository.UserAchievementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserAchievementsUseCase @Inject constructor(
    private val userAchievementRepository: UserAchievementRepository
) {
    operator fun invoke(): Flow<List<UserAchievement>> = userAchievementRepository.observeAll()
}
