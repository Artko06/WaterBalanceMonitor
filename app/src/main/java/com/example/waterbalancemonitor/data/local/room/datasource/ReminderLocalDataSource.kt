package com.example.waterbalancemonitor.data.local.room.datasource

import com.example.waterbalancemonitor.data.local.room.dao.ReminderDao
import com.example.waterbalancemonitor.data.local.room.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderLocalDataSource @Inject constructor(
    private val reminderDao: ReminderDao
) {

    fun observeAll(): Flow<List<ReminderEntity>> = reminderDao.observeAll()

    suspend fun getEnabled(): List<ReminderEntity> = reminderDao.getEnabled()

    suspend fun getById(id: Long): ReminderEntity? = reminderDao.getById(id)

    suspend fun upsert(reminder: ReminderEntity) = reminderDao.upsert(reminder)

    suspend fun setEnabled(id: Long, isEnabled: Boolean) = reminderDao.setEnabled(id, isEnabled)

    suspend fun deleteById(id: Long) = reminderDao.deleteById(id)
}
