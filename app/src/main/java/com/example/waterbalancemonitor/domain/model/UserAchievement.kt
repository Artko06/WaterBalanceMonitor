package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalDateTime

data class UserAchievement(
    val id: Long,
    val achievementId: Long,
    val progress: Int,
    val isUnlocked: Boolean,
    val unlockedAt: LocalDateTime?
)
