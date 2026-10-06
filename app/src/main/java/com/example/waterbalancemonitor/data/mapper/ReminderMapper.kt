package com.example.waterbalancemonitor.data.mapper

import com.example.waterbalancemonitor.data.local.room.entity.ReminderEntity
import com.example.waterbalancemonitor.domain.model.Reminder

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    title = title,
    isEnabled = isEnabled,
    intervalMinutes = intervalMinutes,
    startTime = startTime,
    endTime = endTime
)

fun Reminder.toData(): ReminderEntity = ReminderEntity(
    id = id,
    title = title,
    isEnabled = isEnabled,
    intervalMinutes = intervalMinutes,
    startTime = startTime,
    endTime = endTime
)
