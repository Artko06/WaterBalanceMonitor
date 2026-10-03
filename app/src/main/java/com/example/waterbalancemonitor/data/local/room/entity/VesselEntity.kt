package com.example.waterbalancemonitor.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vessel")
data class VesselEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val volumeMl: Int,
    val icon: String,
    val isDefault: Boolean
)
