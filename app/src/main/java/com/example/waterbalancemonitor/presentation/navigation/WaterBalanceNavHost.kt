package com.example.waterbalancemonitor.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.PlaceholderScreen
import com.example.waterbalancemonitor.presentation.screens.norminfo.NormInfoScreen
import com.example.waterbalancemonitor.presentation.screens.onboarding.OnboardingScreen
import com.example.waterbalancemonitor.presentation.screens.profile.ProfileScreen

@Composable
fun WaterBalanceNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onNavigateToNormInfo = { navController.navigate(Screen.NormInfo.route) }
            )
        }
        composable(Screen.Dashboard.route) { PlaceholderScreen(R.string.nav_dashboard) }
        composable(Screen.History.route) { PlaceholderScreen(R.string.nav_history) }
        composable(Screen.Statistics.route) { PlaceholderScreen(R.string.nav_statistics) }
        composable(Screen.Goals.route) { PlaceholderScreen(R.string.nav_goals) }
        composable(Screen.Achievements.route) { PlaceholderScreen(R.string.nav_achievements) }
        composable(Screen.Reminders.route) { PlaceholderScreen(R.string.nav_reminders) }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToNormInfo = { navController.navigate(Screen.NormInfo.route) }
            )
        }
        composable(Screen.Settings.route) { PlaceholderScreen(R.string.nav_settings) }
        composable(Screen.NormInfo.route) {
            NormInfoScreen()
        }
    }
}
