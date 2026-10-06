package com.example.waterbalancemonitor.domain.model

data class Vessel(
    val id: Long,
    val name: String,
    val volumeMl: Int,
    val icon: VesselIcon,
    val isDefault: Boolean
)
