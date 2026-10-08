package com.example.waterbalancemonitor.presentation.screens.profile.state

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender

data class ProfileState(
    val name: String = "",
    val weightKg: Double? = null,
    val heightCm: Int? = null,
    val age: Int? = null,
    val gender: Gender = Gender.MALE,
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    val calculatedNormMl: Int = 0,
    val isGoalManual: Boolean = false,
    val goalOverrideMl: Int? = null,
    val editingField: ProfileField? = null,
    val draftText: String = "",
    val draftNumber: Float? = null,
    val isLoaded: Boolean = false,
    val showValidation: Boolean = false
) {
    val isWeightValid: Boolean
        get() = weightKg?.let { it in MIN_WEIGHT..MAX_WEIGHT } == true

    val isHeightValid: Boolean
        get() = heightCm?.let { it in MIN_HEIGHT..MAX_HEIGHT } ?: true

    val isAgeValid: Boolean
        get() = age?.let { it in MIN_AGE..MAX_AGE } ?: true

    val isGoalValid: Boolean
        get() = !isGoalManual || goalOverrideMl?.let { it in MIN_GOAL..MAX_GOAL } == true

    val isValid: Boolean
        get() = isWeightValid && isHeightValid && isAgeValid && isGoalValid

    val effectiveNormMl: Int
        get() = if (isGoalManual) {
            goalOverrideMl ?: calculatedNormMl
        } else {
            calculatedNormMl
        }

    companion object {
        const val MIN_WEIGHT = 20.0
        const val MAX_WEIGHT = 400.0
        const val MIN_HEIGHT = 50
        const val MAX_HEIGHT = 250
        const val MIN_AGE = 1
        const val MAX_AGE = 120
        const val MIN_GOAL = 500
        const val MAX_GOAL = 6000

        const val WEIGHT_STEP = 1f
        const val HEIGHT_STEP = 1f
        const val AGE_STEP = 1f
        const val GOAL_STEP = 50f
    }
}
