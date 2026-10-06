package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.DailySummaryEntity
import com.example.waterbalancemonitor.domain.model.DailySummary

fun DailySummaryEntity.toDomain(): DailySummary = DailySummary(
    id = id,
    date = date,
    totalMl = totalMl,
    eventsCount = eventsCount,
    lastEventAt = lastEventAt.toLocalDateTimeOrNull()
)

fun DailySummary.toData(): DailySummaryEntity = DailySummaryEntity(
    id = id,
    date = date,
    totalMl = totalMl,
    eventsCount = eventsCount,
    lastEventAt = lastEventAt.toEpochMillisecondsOrNull()
)
