package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalTime

data class Reminder(
    val id: Long,
    val title: String,
    val isEnabled: Boolean,
    val intervalMinutes: Int,
    val startTime: LocalTime,
    val endTime: LocalTime
)
