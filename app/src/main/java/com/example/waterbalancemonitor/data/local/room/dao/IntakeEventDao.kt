package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.IntakeEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IntakeEventDao {

    @Query("SELECT * FROM intake_event ORDER BY consumedAt DESC")
    fun observeAll(): Flow<List<IntakeEventEntity>>

    @Query(
        "SELECT * FROM intake_event WHERE consumedAt BETWEEN :from AND :to " +
            "ORDER BY consumedAt DESC"
    )
    fun observeBetween(from: Long, to: Long): Flow<List<IntakeEventEntity>>

    @Query(
        "SELECT * FROM intake_event WHERE consumedAt BETWEEN :from AND :to " +
            "ORDER BY consumedAt DESC"
    )
    suspend fun getBetween(from: Long, to: Long): List<IntakeEventEntity>

    @Query("SELECT * FROM intake_event WHERE id = :id")
    suspend fun getById(id: Long): IntakeEventEntity?

    @Query("SELECT COALESCE(SUM(volumeMl), 0) FROM intake_event WHERE consumedAt BETWEEN :from AND :to")
    suspend fun sumVolumeBetween(from: Long, to: Long): Int

    @Query("SELECT COUNT(*) FROM intake_event WHERE consumedAt BETWEEN :from AND :to")
    suspend fun countBetween(from: Long, to: Long): Int

    @Upsert
    suspend fun upsert(event: IntakeEventEntity)

    @Upsert
    suspend fun upsertAll(events: List<IntakeEventEntity>)

    @Query("DELETE FROM intake_event WHERE id = :id")
    suspend fun deleteById(id: Long)
}
