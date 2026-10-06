package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.DrinkTypeEntity
import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.domain.model.DrinkTypeIcon

fun DrinkTypeEntity.toDomain(): DrinkType = DrinkType(
    id = id,
    name = name,
    hydrationCoefficient = hydrationCoefficient,
    icon = icon.toEnumOrDefault(DrinkTypeIcon.DEFAULT),
    isDefault = isDefault
)

fun DrinkType.toData(): DrinkTypeEntity = DrinkTypeEntity(
    id = id,
    name = name,
    hydrationCoefficient = hydrationCoefficient,
    icon = icon.name,
    isDefault = isDefault
)
