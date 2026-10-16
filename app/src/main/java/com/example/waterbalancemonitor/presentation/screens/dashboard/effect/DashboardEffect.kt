package com.example.waterbalancemonitor.presentation.screens.dashboard.effect

sealed interface DashboardEffect {
    data object NavigateToGoalEditing : DashboardEffect
    data object NavigateToHistory : DashboardEffect
    data object ScrollToTop : DashboardEffect
}
