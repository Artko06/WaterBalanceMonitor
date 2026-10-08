package com.example.waterbalancemonitor.presentation.screens.onboarding.action

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender

sealed interface OnboardingAction {

    data class WeightChanged(val value: Float) : OnboardingAction

    data class GenderSelected(val gender: Gender) : OnboardingAction

    data class ActivitySelected(val activityLevel: ActivityLevel) : OnboardingAction

    data class NameChanged(val value: String) : OnboardingAction

    data object NextClicked : OnboardingAction

    data object BackClicked : OnboardingAction

    data object FinishClicked : OnboardingAction

    data object NormInfoClicked : OnboardingAction
}
