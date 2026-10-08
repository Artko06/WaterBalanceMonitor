package com.example.waterbalancemonitor.presentation.screens.onboarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.ActivitySelector
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingState

@Composable
fun ActivityStep(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    OnboardingStepContent(
        title = stringResource(R.string.onboarding_activity_title),
        description = stringResource(R.string.onboarding_activity_desc)
    ) {
        ActivitySelector(
            selected = state.activityLevel,
            onSelected = { onAction(OnboardingAction.ActivitySelected(it)) }
        )
    }
}
