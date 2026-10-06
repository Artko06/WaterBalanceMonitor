package com.example.waterbalancemonitor.presentation.mapper

import androidx.annotation.DrawableRes
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.AchievementIcon

@DrawableRes
fun AchievementIcon.toDrawableRes(): Int = when (this) {
    AchievementIcon.FIRST -> R.drawable.ic_achievement_first
    AchievementIcon.GOAL -> R.drawable.ic_achievement_goal
    AchievementIcon.STREAK -> R.drawable.ic_achievement_streak
    AchievementIcon.VOLUME -> R.drawable.ic_achievement_volume
    AchievementIcon.MORNING -> R.drawable.ic_achievement_morning
    AchievementIcon.NIGHT -> R.drawable.ic_achievement_night
    AchievementIcon.DEFAULT -> R.drawable.ic_achievement_default
}
