package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.UserAchievementEntity
import com.example.waterbalancemonitor.domain.model.UserAchievement

fun UserAchievementEntity.toDomain(): UserAchievement = UserAchievement(
    id = id,
    achievementId = achievementId,
    progress = progress,
    isUnlocked = isUnlocked,
    unlockedAt = unlockedAt.toLocalDateTimeOrNull()
)

fun UserAchievement.toData(): UserAchievementEntity = UserAchievementEntity(
    id = id,
    achievementId = achievementId,
    progress = progress,
    isUnlocked = isUnlocked,
    unlockedAt = unlockedAt.toEpochMillisecondsOrNull()
)
