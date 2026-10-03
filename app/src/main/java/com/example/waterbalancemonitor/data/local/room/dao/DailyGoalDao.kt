package com.example.waterbalancemonitor.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.waterbalancemonitor.data.local.room.entity.DailyGoalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface DailyGoalDao {

    @Query("SELECT * FROM daily_goal WHERE date = :date LIMIT 1")
    fun observeByDate(date: LocalDate): Flow<DailyGoalEntity?>

    @Query("SELECT * FROM daily_goal WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: LocalDate): DailyGoalEntity?

    @Query("SELECT * FROM daily_goal WHERE date BETWEEN :from AND :to ORDER BY date")
    fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailyGoalEntity>>

    @Upsert
    suspend fun upsert(goal: DailyGoalEntity)

    @Query("UPDATE daily_goal SET isCompleted = :isCompleted WHERE date = :date")
    suspend fun setCompleted(date: LocalDate, isCompleted: Boolean)

    @Query("DELETE FROM daily_goal WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}
