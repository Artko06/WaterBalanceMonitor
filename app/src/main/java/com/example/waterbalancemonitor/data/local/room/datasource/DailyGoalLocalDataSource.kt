package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.DailyGoalDao
import com.example.waterbalancemonitor.data.local.room.entity.DailyGoalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class DailyGoalLocalDataSource @Inject constructor(
    private val dailyGoalDao: DailyGoalDao
) {

    fun observeByDate(date: LocalDate): Flow<DailyGoalEntity?> =
        dailyGoalDao.observeByDate(date)

    suspend fun getByDate(date: LocalDate): DailyGoalEntity? = dailyGoalDao.getByDate(date)

    fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailyGoalEntity>> =
        dailyGoalDao.observeBetween(from, to)

    suspend fun upsert(goal: DailyGoalEntity) = dailyGoalDao.upsert(goal)

    suspend fun setCompleted(date: LocalDate, isCompleted: Boolean) =
        dailyGoalDao.setCompleted(date, isCompleted)

    suspend fun deleteByDate(date: LocalDate) = dailyGoalDao.deleteByDate(date)
}
