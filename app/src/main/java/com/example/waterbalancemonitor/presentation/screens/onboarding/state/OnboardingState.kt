package com.example.waterbalancemonitor.presentation.screens.onboarding.state

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender

data class OnboardingState(
    val step: OnboardingStep = OnboardingStep.WELCOME,
    val weightKg: Float = DEFAULT_WEIGHT,
    val gender: Gender = Gender.MALE,
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    val name: String = "",
    val calculatedNormMl: Int = 0
) {
    val progress: Float
        get() = (step.ordinal + 1) / OnboardingStep.entries.size.toFloat()

    val isFirstStep: Boolean
        get() = step == OnboardingStep.WEIGHT

    val isLastStep: Boolean
        get() = step == OnboardingStep.SUMMARY

    companion object {
        const val DEFAULT_WEIGHT = 70f
        const val MIN_WEIGHT = 20f
        const val MAX_WEIGHT = 400f
        const val WEIGHT_STEP = 1f
    }
}
