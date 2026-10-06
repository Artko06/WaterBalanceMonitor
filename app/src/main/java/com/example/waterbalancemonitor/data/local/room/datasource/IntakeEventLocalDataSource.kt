package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.IntakeEventDao
import com.example.waterbalancemonitor.data.local.room.entity.IntakeEventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IntakeEventLocalDataSource @Inject constructor(
    private val intakeEventDao: IntakeEventDao
) {

    fun observeAll(): Flow<List<IntakeEventEntity>> = intakeEventDao.observeAll()

    fun observeBetween(from: Long, to: Long): Flow<List<IntakeEventEntity>> =
        intakeEventDao.observeBetween(from, to)

    suspend fun getBetween(from: Long, to: Long): List<IntakeEventEntity> =
        intakeEventDao.getBetween(from, to)

    suspend fun getById(id: Long): IntakeEventEntity? = intakeEventDao.getById(id)

    suspend fun sumVolumeBetween(from: Long, to: Long): Int =
        intakeEventDao.sumVolumeBetween(from, to)

    suspend fun countBetween(from: Long, to: Long): Int = intakeEventDao.countBetween(from, to)

    suspend fun upsert(event: IntakeEventEntity) = intakeEventDao.upsert(event)

    suspend fun upsertAll(events: List<IntakeEventEntity>) = intakeEventDao.upsertAll(events)

    suspend fun deleteById(id: Long) = intakeEventDao.deleteById(id)
}
