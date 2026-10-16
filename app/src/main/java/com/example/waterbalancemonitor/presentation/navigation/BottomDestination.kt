package com.example.waterbalancemonitor.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.waterbalancemonitor.R

enum class BottomDestination(
    val screen: Screen,
    @param:StringRes val labelRes: Int,
    val icon: ImageVector
) {
    DASHBOARD(Screen.Dashboard, R.string.nav_dashboard, Icons.Filled.Home),
    ACHIEVEMENTS(Screen.Achievements, R.string.nav_achievements, Icons.Filled.Star),
    STATISTICS(Screen.Statistics, R.string.nav_statistics, Icons.Filled.DateRange),
    PROFILE(Screen.Profile, R.string.nav_profile, Icons.Filled.AccountCircle)
}
