package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalDateTime

data class Profile(
    val id: Long,
    val name: String,
    val weightKg: Double,
    val heightCm: Double?,
    val age: Int?,
    val gender: Gender,
    val activityLevel: ActivityLevel,
    val dailyNormMl: Int,
    val updatedAt: LocalDateTime
)
