package com.example.waterbalancemonitor.domain.usecase.profile

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender
import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateDailyNormUseCase @Inject constructor() {

    operator fun invoke(
        weightKg: Double,
        gender: Gender,
        activityLevel: ActivityLevel
    ): Int {
        val baseMlPerKg = when (gender) {
            Gender.MALE -> MALE_ML_PER_KG
            Gender.FEMALE -> FEMALE_ML_PER_KG
        }
        val activityFactor = when (activityLevel) {
            ActivityLevel.SEDENTARY -> 1.0
            ActivityLevel.LIGHT -> 1.1
            ActivityLevel.MODERATE -> 1.2
            ActivityLevel.ACTIVE -> 1.35
            ActivityLevel.VERY_ACTIVE -> 1.5
        }
        val rawMl = weightKg * baseMlPerKg * activityFactor
        return roundToStep(rawMl.coerceIn(MIN_ML.toDouble(), MAX_ML.toDouble()))
    }

    private fun roundToStep(value: Double): Int =
        (value / ROUNDING_STEP).roundToInt() * ROUNDING_STEP

    private companion object {
        const val MALE_ML_PER_KG = 29.0
        const val FEMALE_ML_PER_KG = 27.0
        const val MIN_ML = 1500
        const val MAX_ML = 4000
        const val ROUNDING_STEP = 10
    }
}
