package com.example.waterbalancemonitor.presentation.screens.statistics.state

import kotlinx.datetime.LocalDate

data class StatisticsState(
    val period: StatsPeriod = StatsPeriod.WEEK,
    val totalMl: Int = 0,
    val averageMl: Int = 0,
    val daysCount: Int = 0,
    val completedDays: Int = 0,
    val completedPercent: Int = 0,
    val bestDayMl: Int = 0,
    val bestDate: LocalDate? = null,
    val worstDayMl: Int = 0,
    val worstDate: LocalDate? = null,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val trendPercent: Int? = null,
    val isLoading: Boolean = true
) {
    val hasData: Boolean
        get() = daysCount > 0 || completedDays > 0
}
