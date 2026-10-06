package com.example.waterbalancemonitor.domain.model

data class Achievement(
    val id: Long,
    val code: String,
    val title: String,
    val description: String,
    val category: AchievementCategory,
    val conditionType: AchievementConditionType,
    val conditionValue: Int,
    val icon: AchievementIcon
)
