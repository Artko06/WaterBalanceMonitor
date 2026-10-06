package com.example.waterbalancemonitor.data.mapper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

private val systemTimeZone = TimeZone.currentSystemDefault()

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(systemTimeZone)

fun Long?.toLocalDateTimeOrNull(): LocalDateTime? =
    this?.toLocalDateTime()

fun LocalDateTime.toEpochMilliseconds(): Long =
    this.toInstant(systemTimeZone).toEpochMilliseconds()

fun LocalDateTime?.toEpochMillisecondsOrNull(): Long? =
    this?.toEpochMilliseconds()
