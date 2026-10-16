package com.example.waterbalancemonitor.presentation.screens.achievements.action

sealed interface AchievementsAction {
    data object Refresh : AchievementsAction
}
