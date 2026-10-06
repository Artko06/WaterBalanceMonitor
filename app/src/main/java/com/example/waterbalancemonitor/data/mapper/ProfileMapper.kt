package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.ProfileEntity
import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.domain.model.Profile

fun ProfileEntity.toDomain(): Profile = Profile(
    id = id,
    name = name,
    weightKg = weightKg,
    heightCm = heightCm,
    age = age,
    gender = gender.toEnumOrDefault(Gender.MALE),
    activityLevel = activityLevel.toEnumOrDefault(ActivityLevel.MODERATE),
    dailyNormMl = dailyNormMl,
    updatedAt = updatedAt.toLocalDateTime()
)

fun Profile.toData(): ProfileEntity = ProfileEntity(
    id = id,
    name = name,
    weightKg = weightKg,
    heightCm = heightCm,
    age = age,
    gender = gender.name,
    activityLevel = activityLevel.name,
    dailyNormMl = dailyNormMl,
    updatedAt = updatedAt.toEpochMilliseconds()
)
