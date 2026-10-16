package com.example.waterbalancemonitor.presentation.screens.achievements

import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import com.example.waterbalancemonitor.domain.model.UserAchievement
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementGroup
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementItem

fun buildAchievementItems(
    achievements: List<Achievement>,
    userAchievements: List<UserAchievement>
): List<AchievementItem> {
    val userById = userAchievements.associateBy { it.achievementId }
    val completedCodes = achievements
        .filter { userById[it.id]?.isCompleted == true }
        .map { it.code }
        .toSet()
    return achievements.sortedBy { it.id }.map { achievement ->
        val user = userById[achievement.id]
        val available = achievement.prerequisiteCode == null ||
            achievement.prerequisiteCode in completedCodes
        val status = when {
            user?.isCompleted == true -> AchievementStatus.COMPLETED
            !available -> AchievementStatus.LOCKED
            else -> AchievementStatus.IN_PROGRESS
        }
        AchievementItem(
            id = achievement.id,
            title = achievement.title,
            description = achievement.description,
            icon = achievement.icon,
            category = achievement.category,
            progress = user?.progress ?: 0,
            target = achievement.conditionValue,
            status = status
        )
    }
}

fun groupAchievements(items: List<AchievementItem>): List<AchievementGroup> =
    AchievementCategory.entries.mapNotNull { category ->
        items.filter { it.category == category }
            .takeIf { it.isNotEmpty() }
            ?.let { AchievementGroup(category, it) }
    }
