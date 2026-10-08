package com.example.waterbalancemonitor.presentation.navigation

import androidx.navigation.NavController

internal fun NavController.navigateToBottomDestination(screen: Screen) {
    navigate(screen.route) {
        popUpTo(Screen.Dashboard.route)
        launchSingleTop = true
    }
}
