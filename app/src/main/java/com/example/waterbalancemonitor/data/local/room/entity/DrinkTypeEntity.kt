package com.example.waterbalancemonitor.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_type")
data class DrinkTypeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val hydrationCoefficient: Double,
    val icon: String,
    val isDefault: Boolean
)
