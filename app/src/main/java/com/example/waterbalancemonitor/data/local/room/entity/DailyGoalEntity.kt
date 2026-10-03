package com.example.waterbalancemonitor.data.local.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "daily_goal",
    indices = [Index(value = ["date"], unique = true)]
)
data class DailyGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val targetMl: Int,
    val baseNormMl: Int,
    val bonusMl: Int,
    val isCompleted: Boolean
)
