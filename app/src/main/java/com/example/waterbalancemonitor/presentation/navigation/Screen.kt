package com.example.waterbalancemonitor.presentation.navigation

import com.example.waterbalancemonitor.domain.model.AchievementCategory

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object History : Screen("history")
    data object Statistics : Screen("statistics")
    data object Goals : Screen("goals")
    data object Achievements : Screen("achievements")
    data object AchievementGroup : Screen("achievement_group/{${Screen.ARG_CATEGORY}}") {
        fun path(category: AchievementCategory): String =
            "achievement_group/${category.name}"
    }
    data object Reminders : Screen("reminders")
    data object Profile : Screen("profile")
    data object NormInfo : Screen("norm_info")
    data object Onboarding : Screen("onboarding")

    companion object {
        const val ARG_CATEGORY = "category"
    }
}
