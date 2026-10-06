package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.VesselEntity
import com.example.waterbalancemonitor.domain.model.Vessel
import com.example.waterbalancemonitor.domain.model.VesselIcon

fun VesselEntity.toDomain(): Vessel = Vessel(
    id = id,
    name = name,
    volumeMl = volumeMl,
    icon = icon.toEnumOrDefault(VesselIcon.DEFAULT),
    isDefault = isDefault
)

fun Vessel.toData(): VesselEntity = VesselEntity(
    id = id,
    name = name,
    volumeMl = volumeMl,
    icon = icon.name,
    isDefault = isDefault
)
