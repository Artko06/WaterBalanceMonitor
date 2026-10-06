package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class DailySummary(
    val id: Long,
    val date: LocalDate,
    val totalMl: Int,
    val eventsCount: Int,
    val lastEventAt: LocalDateTime?
)
