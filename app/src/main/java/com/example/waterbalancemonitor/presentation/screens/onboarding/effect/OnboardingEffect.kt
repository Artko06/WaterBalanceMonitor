package com.example.waterbalancemonitor.presentation.screens.onboarding.effect

sealed interface OnboardingEffect {
    data object Finished : OnboardingEffect
    data object NavigateToNormInfo : OnboardingEffect
}
