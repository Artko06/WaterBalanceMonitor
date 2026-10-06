package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {

    fun observeAll(): Flow<List<Achievement>>

    suspend fun getAll(): List<Achievement>

    suspend fun getByCode(code: String): Achievement?

    suspend fun save(achievement: Achievement)

    suspend fun saveAll(achievements: List<Achievement>)
}
