package com.example.waterbalancemonitor.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val weightKg: Double,
    val heightCm: Double,
    val age: Int,
    val gender: String,
    val activityLevel: String,
    val dailyNormMl: Int,
    val updatedAt: Long
)
