package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    fun observeAll(): Flow<List<Reminder>>

    suspend fun getEnabled(): List<Reminder>

    suspend fun getById(id: Long): Reminder?

    suspend fun save(reminder: Reminder)

    suspend fun setEnabled(id: Long, isEnabled: Boolean)

    suspend fun deleteById(id: Long)
}
