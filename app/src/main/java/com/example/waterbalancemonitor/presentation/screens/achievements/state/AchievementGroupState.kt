package com.example.waterbalancemonitor.presentation.screens.achievements.state

data class AchievementGroupState(
    val items: List<AchievementItem> = emptyList(),
    val isLoading: Boolean = true
)
