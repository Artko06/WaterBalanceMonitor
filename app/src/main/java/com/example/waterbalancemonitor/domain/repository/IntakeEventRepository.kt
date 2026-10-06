package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface IntakeEventRepository {

    fun observeAll(): Flow<List<IntakeEvent>>

    fun observeBetween(from: LocalDateTime, to: LocalDateTime): Flow<List<IntakeEvent>>

    suspend fun getBetween(from: LocalDateTime, to: LocalDateTime): List<IntakeEvent>

    suspend fun getById(id: Long): IntakeEvent?

    suspend fun save(event: IntakeEvent)

    suspend fun saveAll(events: List<IntakeEvent>)

    suspend fun deleteById(id: Long)

    suspend fun sumVolumeBetween(from: LocalDateTime, to: LocalDateTime): Int

    suspend fun countBetween(from: LocalDateTime, to: LocalDateTime): Int
}
