package com.example.waterbalancemonitor.domain.model

data class DrinkType(
    val id: Long,
    val name: String,
    val hydrationCoefficient: Double,
    val icon: DrinkTypeIcon,
    val isDefault: Boolean
)
