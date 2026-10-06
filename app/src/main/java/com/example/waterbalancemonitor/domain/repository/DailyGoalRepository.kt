package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.DailyGoal
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface DailyGoalRepository {

    fun observeByDate(date: LocalDate): Flow<DailyGoal?>

    suspend fun getByDate(date: LocalDate): DailyGoal?

    fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailyGoal>>

    suspend fun save(goal: DailyGoal)

    suspend fun setCompleted(date: LocalDate, isCompleted: Boolean)

    suspend fun deleteByDate(date: LocalDate)
}
