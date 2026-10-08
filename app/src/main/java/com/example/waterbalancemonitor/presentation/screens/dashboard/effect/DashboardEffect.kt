package com.example.waterbalancemonitor.presentation.screens.dashboard.effect

sealed interface DashboardEffect {
    data object NavigateToGoalEditing : DashboardEffect
}
