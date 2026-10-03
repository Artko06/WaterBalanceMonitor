package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.DrinkTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkTypeDao {

    @Query("SELECT * FROM drink_type ORDER BY name")
    fun observeAll(): Flow<List<DrinkTypeEntity>>

    @Query("SELECT * FROM drink_type ORDER BY name")
    suspend fun getAll(): List<DrinkTypeEntity>

    @Query("SELECT * FROM drink_type WHERE id = :id")
    suspend fun getById(id: Long): DrinkTypeEntity?

    @Insert
    suspend fun insertAll(items: List<DrinkTypeEntity>)

    @Upsert
    suspend fun upsert(item: DrinkTypeEntity)

    @Query("DELETE FROM drink_type WHERE id = :id")
    suspend fun deleteById(id: Long)
}
