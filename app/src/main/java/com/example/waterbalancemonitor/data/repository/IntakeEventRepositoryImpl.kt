package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.IntakeEventLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.data.mapper.toEpochMilliseconds
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class IntakeEventRepositoryImpl @Inject constructor(
    private val intakeEventLocalDataSource: IntakeEventLocalDataSource
) : IntakeEventRepository {

    override fun observeAll(): Flow<List<IntakeEvent>> =
        intakeEventLocalDataSource.observeAll().map { events -> events.map { it.toDomain() } }

    override fun observeBetween(from: LocalDateTime, to: LocalDateTime): Flow<List<IntakeEvent>> =
        intakeEventLocalDataSource
            .observeBetween(from.toEpochMilliseconds(), to.toEpochMilliseconds())
            .map { events -> events.map { it.toDomain() } }

    override suspend fun getBetween(
        from: LocalDateTime,
        to: LocalDateTime
    ): List<IntakeEvent> =
        intakeEventLocalDataSource
            .getBetween(from.toEpochMilliseconds(), to.toEpochMilliseconds())
            .map { it.toDomain() }

    override suspend fun getById(id: Long): IntakeEvent? =
        intakeEventLocalDataSource.getById(id)?.toDomain()

    override suspend fun save(event: IntakeEvent) =
        intakeEventLocalDataSource.upsert(event.toData())

    override suspend fun saveAll(events: List<IntakeEvent>) =
        intakeEventLocalDataSource.upsertAll(events.map { it.toData() })

    override suspend fun deleteById(id: Long) = intakeEventLocalDataSource.deleteById(id)

    override suspend fun sumVolumeBetween(from: LocalDateTime, to: LocalDateTime): Int =
        intakeEventLocalDataSource.sumVolumeBetween(
            from.toEpochMilliseconds(),
            to.toEpochMilliseconds()
        )

    override suspend fun countBetween(from: LocalDateTime, to: LocalDateTime): Int =
        intakeEventLocalDataSource.countBetween(
            from.toEpochMilliseconds(),
            to.toEpochMilliseconds()
        )
}
