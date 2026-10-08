package com.example.waterbalancemonitor.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "intake_event",
    foreignKeys = [
        ForeignKey(
            entity = DrinkTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["drinkTypeId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = VesselEntity::class,
            parentColumns = ["id"],
            childColumns = ["vesselId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("drinkTypeId"), Index("vesselId"), Index("consumedAt")]
)
data class IntakeEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val drinkTypeId: Long,
    val vesselId: Long?,
    val volumeMl: Int,
    val consumedAt: Long
)
