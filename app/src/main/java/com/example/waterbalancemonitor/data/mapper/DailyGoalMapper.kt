package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.DailyGoalEntity
import com.example.waterbalancemonitor.domain.model.DailyGoal

fun DailyGoalEntity.toDomain(): DailyGoal = DailyGoal(
    id = id,
    date = date,
    targetMl = targetMl,
    baseNormMl = baseNormMl,
    bonusMl = bonusMl,
    isCompleted = isCompleted
)

fun DailyGoal.toData(): DailyGoalEntity = DailyGoalEntity(
    id = id,
    date = date,
    targetMl = targetMl,
    baseNormMl = baseNormMl,
    bonusMl = bonusMl,
    isCompleted = isCompleted
)
