package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.UserAchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAchievementDao {

    @Query("SELECT * FROM user_achievement ORDER BY achievementId")
    fun observeAll(): Flow<List<UserAchievementEntity>>

    @Query("SELECT * FROM user_achievement WHERE achievementId = :achievementId LIMIT 1")
    fun observeByAchievementId(achievementId: Long): Flow<UserAchievementEntity?>

    @Query("SELECT * FROM user_achievement WHERE achievementId = :achievementId LIMIT 1")
    suspend fun getByAchievementId(achievementId: Long): UserAchievementEntity?

    @Upsert
    suspend fun upsert(item: UserAchievementEntity)

    @Upsert
    suspend fun upsertAll(items: List<UserAchievementEntity>)

    @Query("DELETE FROM user_achievement WHERE achievementId = :achievementId")
    suspend fun deleteByAchievementId(achievementId: Long)
}
