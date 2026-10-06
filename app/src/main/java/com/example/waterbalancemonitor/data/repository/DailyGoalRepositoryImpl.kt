package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.DailyGoalLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class DailyGoalRepositoryImpl @Inject constructor(
    private val dailyGoalLocalDataSource: DailyGoalLocalDataSource
) : DailyGoalRepository {

    override fun observeByDate(date: LocalDate): Flow<DailyGoal?> =
        dailyGoalLocalDataSource.observeByDate(date).map { it?.toDomain() }

    override suspend fun getByDate(date: LocalDate): DailyGoal? =
        dailyGoalLocalDataSource.getByDate(date)?.toDomain()

    override fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailyGoal>> =
        dailyGoalLocalDataSource.observeBetween(from, to).map { items ->
            items.map { it.toDomain() }
        }

    override suspend fun save(goal: DailyGoal) = dailyGoalLocalDataSource.upsert(goal.toData())

    override suspend fun setCompleted(date: LocalDate, isCompleted: Boolean) =
        dailyGoalLocalDataSource.setCompleted(date, isCompleted)

    override suspend fun deleteByDate(date: LocalDate) =
        dailyGoalLocalDataSource.deleteByDate(date)
}
