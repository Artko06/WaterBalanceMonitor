package com.example.waterbalancemonitor.presentation.screens.achievements.state

import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementStatus

data class AchievementGroup(
    val category: AchievementCategory,
    val items: List<AchievementItem>
) {
    val completedCount: Int
        get() = items.count { it.status == AchievementStatus.COMPLETED }

    val totalCount: Int
        get() = items.size

    val progress: Float
        get() = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
}
