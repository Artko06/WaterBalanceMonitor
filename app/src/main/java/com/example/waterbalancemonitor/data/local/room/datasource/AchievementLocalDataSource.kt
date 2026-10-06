package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.AchievementDao
import com.example.waterbalancemonitor.data.local.room.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AchievementLocalDataSource @Inject constructor(
    private val achievementDao: AchievementDao
) {

    fun observeAll(): Flow<List<AchievementEntity>> = achievementDao.observeAll()

    suspend fun getAll(): List<AchievementEntity> = achievementDao.getAll()

    suspend fun getByCode(code: String): AchievementEntity? = achievementDao.getByCode(code)

    suspend fun insertAll(items: List<AchievementEntity>) = achievementDao.insertAll(items)

    suspend fun upsert(item: AchievementEntity) = achievementDao.upsert(item)
}
