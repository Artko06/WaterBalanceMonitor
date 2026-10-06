package com.example.waterbalancemonitor.domain.usecase.profile

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateDailyNormUseCase @Inject constructor() {

    operator fun invoke(weightKg: Double, activityLevel: ActivityLevel): Int {
        val activityFactor = when (activityLevel) {
            ActivityLevel.SEDENTARY -> 1.0
            ActivityLevel.LIGHT -> 1.1
            ActivityLevel.MODERATE -> 1.2
            ActivityLevel.ACTIVE -> 1.3
            ActivityLevel.VERY_ACTIVE -> 1.4
        }
        return (weightKg * ML_PER_KG * activityFactor).roundToInt()
    }

    private companion object {
        const val ML_PER_KG = 30.0
    }
}
