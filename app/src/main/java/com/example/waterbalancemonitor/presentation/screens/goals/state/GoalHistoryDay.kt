package com.example.waterbalancemonitor.presentation.screens.goals.state

import kotlinx.datetime.LocalDate
import kotlin.math.roundToInt

data class GoalHistoryDay(
    val date: LocalDate,
    val consumedMl: Int,
    val targetMl: Int,
    val isCompleted: Boolean
) {
    val progress: Float
        get() = if (targetMl > 0) (consumedMl.toFloat() / targetMl).coerceIn(0f, 1f) else 0f

    val percent: Int
        get() = (progress * 100).roundToInt()
}
