package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.UserAchievementDao
import com.example.waterbalancemonitor.data.local.room.entity.UserAchievementEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAchievementLocalDataSource @Inject constructor(
    private val userAchievementDao: UserAchievementDao
) {

    fun observeAll(): Flow<List<UserAchievementEntity>> = userAchievementDao.observeAll()

    fun observeByAchievementId(achievementId: Long): Flow<UserAchievementEntity?> =
        userAchievementDao.observeByAchievementId(achievementId)

    suspend fun getByAchievementId(achievementId: Long): UserAchievementEntity? =
        userAchievementDao.getByAchievementId(achievementId)

    suspend fun upsert(item: UserAchievementEntity) = userAchievementDao.upsert(item)

    suspend fun upsertAll(items: List<UserAchievementEntity>) = userAchievementDao.upsertAll(items)

    suspend fun deleteByAchievementId(achievementId: Long) =
        userAchievementDao.deleteByAchievementId(achievementId)
}
