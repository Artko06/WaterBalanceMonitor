package com.example.waterbalancemonitor.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalTime

@Entity(tableName = "reminder")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val isEnabled: Boolean,
    val intervalMinutes: Int,
    val startTime: LocalTime,
    val endTime: LocalTime
)
