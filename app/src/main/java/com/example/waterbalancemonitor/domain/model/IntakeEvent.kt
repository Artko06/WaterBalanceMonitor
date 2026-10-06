package com.example.waterbalancemonitor.domain.model

import kotlinx.datetime.LocalDateTime

data class IntakeEvent(
    val id: Long,
    val drinkTypeId: Long,
    val vesselId: Long,
    val volumeMl: Int,
    val consumedAt: LocalDateTime
)
