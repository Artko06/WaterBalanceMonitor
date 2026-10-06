package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.IntakeEventEntity
import com.example.waterbalancemonitor.domain.model.IntakeEvent

fun IntakeEventEntity.toDomain(): IntakeEvent = IntakeEvent(
    id = id,
    drinkTypeId = drinkTypeId,
    vesselId = vesselId,
    volumeMl = volumeMl,
    consumedAt = consumedAt.toLocalDateTime()
)

fun IntakeEvent.toData(): IntakeEventEntity = IntakeEventEntity(
    id = id,
    drinkTypeId = drinkTypeId,
    vesselId = vesselId,
    volumeMl = volumeMl,
    consumedAt = consumedAt.toEpochMilliseconds()
)
