package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.DailySummaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface DailySummaryDao {

    @Query("SELECT * FROM daily_summary WHERE date = :date LIMIT 1")
    fun observeByDate(date: LocalDate): Flow<DailySummaryEntity?>

    @Query("SELECT * FROM daily_summary WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: LocalDate): DailySummaryEntity?

    @Query("SELECT * FROM daily_summary WHERE date BETWEEN :from AND :to ORDER BY date")
    fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailySummaryEntity>>

    @Query("SELECT * FROM daily_summary WHERE date BETWEEN :from AND :to ORDER BY date")
    suspend fun getBetween(from: LocalDate, to: LocalDate): List<DailySummaryEntity>

    @Upsert
    suspend fun upsert(summary: DailySummaryEntity)

    @Query("DELETE FROM daily_summary WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}
