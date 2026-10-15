package com.example.waterbalancemonitor.presentation.screens.goals.state

import kotlin.math.roundToInt

data class GoalsState(
    val name: String = "",
    val baseNormMl: Int = 0,
    val bonusMl: Int = 0,
    val targetMl: Int = 0,
    val consumedMl: Int = 0,
    val history: List<GoalHistoryDay> = emptyList(),
    val isEditBonusOpen: Boolean = false,
    val bonusDraftMl: Int = DEFAULT_BONUS_ML,
    val isLoading: Boolean = true
) {
    val remainingMl: Int
        get() = (targetMl - consumedMl).coerceAtLeast(0)

    val progress: Float
        get() = if (targetMl > 0) (consumedMl.toFloat() / targetMl).coerceIn(0f, 1f) else 0f

    val percent: Int
        get() = (progress * 100).roundToInt()

    val isCompleted: Boolean
        get() = targetMl > 0 && consumedMl >= targetMl

    companion object {
        const val DEFAULT_BONUS_ML = 0
        const val MIN_BONUS_ML = 0
        const val MAX_BONUS_ML = 2000
        const val BONUS_STEP = 50
    }
}
