package com.example.waterbalancemonitor.presentation.screens.statistics.action

import com.example.waterbalancemonitor.presentation.screens.statistics.state.StatsPeriod

sealed interface StatisticsAction {
    data class PeriodSelected(val period: StatsPeriod) : StatisticsAction
}
