package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.DailySummary
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface DailySummaryRepository {

    fun observeByDate(date: LocalDate): Flow<DailySummary?>

    suspend fun getByDate(date: LocalDate): DailySummary?

    fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailySummary>>

    suspend fun getBetween(from: LocalDate, to: LocalDate): List<DailySummary>

    suspend fun save(summary: DailySummary)

    suspend fun deleteByDate(date: LocalDate)
}
