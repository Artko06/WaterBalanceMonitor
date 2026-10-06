package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.AchievementLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val achievementLocalDataSource: AchievementLocalDataSource
) : AchievementRepository {

    override fun observeAll(): Flow<List<Achievement>> =
        achievementLocalDataSource.observeAll().map { items -> items.map { it.toDomain() } }

    override suspend fun getAll(): List<Achievement> =
        achievementLocalDataSource.getAll().map { it.toDomain() }

    override suspend fun getByCode(code: String): Achievement? =
        achievementLocalDataSource.getByCode(code)?.toDomain()

    override suspend fun save(achievement: Achievement) =
        achievementLocalDataSource.upsert(achievement.toData())

    override suspend fun saveAll(achievements: List<Achievement>) =
        achievementLocalDataSource.insertAll(achievements.map { it.toData() })
}
