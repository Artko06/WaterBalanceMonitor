package com.example.waterbalancemonitor.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.PlaceholderScreen

const val ANIMATION_SPEC = 300


@Composable
fun WaterBalanceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(animationSpec = tween(ANIMATION_SPEC))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(ANIMATION_SPEC))
        }
    ) {
        composable(Screen.Dashboard.route) { PlaceholderScreen(R.string.nav_dashboard) }
        composable(Screen.History.route) { PlaceholderScreen(R.string.nav_history) }
        composable(Screen.Statistics.route) { PlaceholderScreen(R.string.nav_statistics) }
        composable(Screen.Goals.route) { PlaceholderScreen(R.string.nav_goals) }
        composable(Screen.Achievements.route) { PlaceholderScreen(R.string.nav_achievements) }
        composable(Screen.Reminders.route) { PlaceholderScreen(R.string.nav_reminders) }
        composable(Screen.Profile.route) { PlaceholderScreen(R.string.nav_profile) }
        composable(Screen.Settings.route) { PlaceholderScreen(R.string.nav_settings) }
    }
}
