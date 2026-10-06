package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.AchievementEntity
import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementConditionType
import com.example.waterbalancemonitor.domain.model.AchievementIcon

fun AchievementEntity.toDomain(): Achievement = Achievement(
    id = id,
    code = code,
    title = title,
    description = description,
    category = category.toEnumOrDefault(AchievementCategory.OTHER),
    conditionType = conditionType.toEnumOrDefault(AchievementConditionType.UNKNOWN),
    conditionValue = conditionValue,
    icon = icon.toEnumOrDefault(AchievementIcon.DEFAULT)
)

fun Achievement.toData(): AchievementEntity = AchievementEntity(
    id = id,
    code = code,
    title = title,
    description = description,
    category = category.name,
    conditionType = conditionType.name,
    conditionValue = conditionValue,
    icon = icon.name
)
