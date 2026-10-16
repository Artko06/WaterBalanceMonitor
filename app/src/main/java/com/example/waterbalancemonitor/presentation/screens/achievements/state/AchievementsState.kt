package com.example.waterbalancemonitor.presentation.screens.achievements.state

data class AchievementsState(
    val groups: List<AchievementGroup> = emptyList(),
    val completedCount: Int = 0,
    val totalCount: Int = 0,
    val isLoading: Boolean = true
)
