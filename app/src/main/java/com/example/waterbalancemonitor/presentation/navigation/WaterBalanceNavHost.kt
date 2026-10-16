package com.example.waterbalancemonitor.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.PlaceholderScreen
import com.example.waterbalancemonitor.presentation.screens.dashboard.DashboardScreen
import com.example.waterbalancemonitor.presentation.screens.goals.GoalsScreen
import com.example.waterbalancemonitor.presentation.screens.history.HistoryScreen
import com.example.waterbalancemonitor.presentation.screens.norminfo.NormInfoScreen
import com.example.waterbalancemonitor.presentation.screens.onboarding.OnboardingScreen
import com.example.waterbalancemonitor.presentation.screens.profile.ProfileScreen
import com.example.waterbalancemonitor.presentation.screens.statistics.StatisticsScreen

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
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToGoal = { navController.navigate(Screen.Goals.route) },
                onNavigateToHistory = { navController.navigate(Screen.History.route) }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.Statistics.route) {
            StatisticsScreen()
        }
        composable(Screen.Goals.route) {
            GoalsScreen()
        }
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
