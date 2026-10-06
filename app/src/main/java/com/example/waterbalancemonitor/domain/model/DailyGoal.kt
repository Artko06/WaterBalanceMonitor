package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalDate

data class DailyGoal(
    val id: Long,
    val date: LocalDate,
    val targetMl: Int,
    val baseNormMl: Int,
    val bonusMl: Int,
    val isCompleted: Boolean
)
