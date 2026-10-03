package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.VesselEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VesselDao {

    @Query("SELECT * FROM vessel ORDER BY name")
    fun observeAll(): Flow<List<VesselEntity>>

    @Query("SELECT * FROM vessel ORDER BY name")
    suspend fun getAll(): List<VesselEntity>

    @Query("SELECT * FROM vessel WHERE id = :id")
    suspend fun getById(id: Long): VesselEntity?

    @Insert
    suspend fun insertAll(items: List<VesselEntity>)

    @Upsert
    suspend fun upsert(item: VesselEntity)

    @Query("DELETE FROM vessel WHERE id = :id")
    suspend fun deleteById(id: Long)
}
