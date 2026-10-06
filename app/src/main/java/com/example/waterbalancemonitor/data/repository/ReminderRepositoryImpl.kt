package com.example.waterbalancemonitor.data.repository

import com.example.waterbalancemonitor.data.local.room.datasource.ReminderLocalDataSource
import com.example.waterbalancemonitor.data.mapper.toData
import com.example.waterbalancemonitor.data.mapper.toDomain
import com.example.waterbalancemonitor.domain.model.Reminder
import com.example.waterbalancemonitor.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderLocalDataSource: ReminderLocalDataSource
) : ReminderRepository {

    override fun observeAll(): Flow<List<Reminder>> =
        reminderLocalDataSource.observeAll().map { items -> items.map { it.toDomain() } }

    override suspend fun getEnabled(): List<Reminder> =
        reminderLocalDataSource.getEnabled().map { it.toDomain() }

    override suspend fun getById(id: Long): Reminder? =
        reminderLocalDataSource.getById(id)?.toDomain()

    override suspend fun save(reminder: Reminder) =
        reminderLocalDataSource.upsert(reminder.toData())

    override suspend fun setEnabled(id: Long, isEnabled: Boolean) =
        reminderLocalDataSource.setEnabled(id, isEnabled)

    override suspend fun deleteById(id: Long) = reminderLocalDataSource.deleteById(id)
}
