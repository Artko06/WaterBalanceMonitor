package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {

    @Query("SELECT * FROM achievement ORDER BY category, title")
    fun observeAll(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievement ORDER BY category, title")
    suspend fun getAll(): List<AchievementEntity>

    @Query("SELECT * FROM achievement WHERE code = :code")
    suspend fun getByCode(code: String): AchievementEntity?

    @Insert
    suspend fun insertAll(items: List<AchievementEntity>)

    @Upsert
    suspend fun upsert(item: AchievementEntity)
}
