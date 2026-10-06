package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.UserAchievementLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.UserAchievement
import com.example.waterbalancemonitor.domain.repository.UserAchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserAchievementRepositoryImpl @Inject constructor(
    private val userAchievementLocalDataSource: UserAchievementLocalDataSource
) : UserAchievementRepository {

    override fun observeAll(): Flow<List<UserAchievement>> =
        userAchievementLocalDataSource.observeAll().map { items ->
            items.map { it.toDomain() }
        }

    override fun observeByAchievementId(achievementId: Long): Flow<UserAchievement?> =
        userAchievementLocalDataSource.observeByAchievementId(achievementId).map { it?.toDomain() }

    override suspend fun getByAchievementId(achievementId: Long): UserAchievement? =
        userAchievementLocalDataSource.getByAchievementId(achievementId)?.toDomain()

    override suspend fun save(userAchievement: UserAchievement) =
        userAchievementLocalDataSource.upsert(userAchievement.toData())

    override suspend fun saveAll(userAchievements: List<UserAchievement>) =
        userAchievementLocalDataSource.upsertAll(userAchievements.map { it.toData() })

    override suspend fun deleteByAchievementId(achievementId: Long) =
        userAchievementLocalDataSource.deleteByAchievementId(achievementId)
}
