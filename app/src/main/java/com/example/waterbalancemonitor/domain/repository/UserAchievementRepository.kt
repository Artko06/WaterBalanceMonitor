package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.UserAchievement
import kotlinx.coroutines.flow.Flow

interface UserAchievementRepository {

    fun observeAll(): Flow<List<UserAchievement>>

    fun observeByAchievementId(achievementId: Long): Flow<UserAchievement?>

    suspend fun getByAchievementId(achievementId: Long): UserAchievement?

    suspend fun save(userAchievement: UserAchievement)

    suspend fun saveAll(userAchievements: List<UserAchievement>)

    suspend fun deleteByAchievementId(achievementId: Long)
}
