package com.example.waterbalancemonitor.presentation.screens.achievements.state

import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementIcon
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import kotlin.math.roundToInt

data class AchievementItem(
    val id: Long,
    val code: String,
    val title: String,
    val description: String,
    val icon: AchievementIcon,
    val category: AchievementCategory,
    val progress: Int,
    val target: Int,
    val status: AchievementStatus
) {
    val progressFraction: Float
        get() = if (target > 0) (progress.toFloat() / target).coerceIn(0f, 1f) else 0f

    val progressPercent: Int
        get() = (progressFraction * 100).roundToInt()
}
