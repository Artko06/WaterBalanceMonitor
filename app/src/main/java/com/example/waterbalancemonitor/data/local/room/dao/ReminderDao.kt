package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder ORDER BY startTime")
    fun observeAll(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminder WHERE isEnabled = 1 ORDER BY startTime")
    suspend fun getEnabled(): List<ReminderEntity>

    @Query("SELECT * FROM reminder WHERE id = :id")
    suspend fun getById(id: Long): ReminderEntity?

    @Upsert
    suspend fun upsert(reminder: ReminderEntity)

    @Query("UPDATE reminder SET isEnabled = :isEnabled WHERE id = :id")
    suspend fun setEnabled(id: Long, isEnabled: Boolean)

    @Query("DELETE FROM reminder WHERE id = :id")
    suspend fun deleteById(id: Long)
}
