package com.example.waterbalancemonitor.presentation.navigation

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object History : Screen("history")
    data object Statistics : Screen("statistics")
    data object Goals : Screen("goals")
    data object Achievements : Screen("achievements")
    data object Reminders : Screen("reminders")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
}
