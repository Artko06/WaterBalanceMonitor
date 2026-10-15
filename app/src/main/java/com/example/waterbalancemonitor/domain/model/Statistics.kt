package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalDate

data class Statistics(
    val from: LocalDate,
    val to: LocalDate,
    val totalMl: Int,
    val averageMl: Int,
    val daysCount: Int,
    val completedDays: Int,
    val completedPercent: Int,
    val bestDayMl: Int,
    val bestDate: LocalDate?,
    val worstDayMl: Int,
    val worstDate: LocalDate?
)
