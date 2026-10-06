package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.DailySummaryLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class DailySummaryRepositoryImpl @Inject constructor(
    private val dailySummaryLocalDataSource: DailySummaryLocalDataSource
) : DailySummaryRepository {

    override fun observeByDate(date: LocalDate): Flow<DailySummary?> =
        dailySummaryLocalDataSource.observeByDate(date).map { it?.toDomain() }

    override suspend fun getByDate(date: LocalDate): DailySummary? =
        dailySummaryLocalDataSource.getByDate(date)?.toDomain()

    override fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailySummary>> =
        dailySummaryLocalDataSource.observeBetween(from, to).map { items ->
            items.map { it.toDomain() }
        }

    override suspend fun getBetween(from: LocalDate, to: LocalDate): List<DailySummary> =
        dailySummaryLocalDataSource.getBetween(from, to).map { it.toDomain() }

    override suspend fun save(summary: DailySummary) =
        dailySummaryLocalDataSource.upsert(summary.toData())

    override suspend fun deleteByDate(date: LocalDate) =
        dailySummaryLocalDataSource.deleteByDate(date)
}
