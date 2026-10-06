package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.DailySummaryDao
import com.example.waterbalancemonitor.data.local.room.entity.DailySummaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class DailySummaryLocalDataSource @Inject constructor(
    private val dailySummaryDao: DailySummaryDao
) {

    fun observeByDate(date: LocalDate): Flow<DailySummaryEntity?> =
        dailySummaryDao.observeByDate(date)

    suspend fun getByDate(date: LocalDate): DailySummaryEntity? = dailySummaryDao.getByDate(date)

    fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailySummaryEntity>> =
        dailySummaryDao.observeBetween(from, to)

    suspend fun getBetween(from: LocalDate, to: LocalDate): List<DailySummaryEntity> =
        dailySummaryDao.getBetween(from, to)

    suspend fun upsert(summary: DailySummaryEntity) = dailySummaryDao.upsert(summary)

    suspend fun deleteByDate(date: LocalDate) = dailySummaryDao.deleteByDate(date)
}
