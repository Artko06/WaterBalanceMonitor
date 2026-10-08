package com.example.waterbalancemonitor.presentation.screens.onboarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.GenderSelector
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingState

@Composable
fun GenderStep(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    OnboardingStepContent(
        title = stringResource(R.string.onboarding_gender_title),
        description = stringResource(R.string.onboarding_gender_desc)
    ) {
        GenderSelector(
            selected = state.gender,
            onSelected = { onAction(OnboardingAction.GenderSelected(it)) }
        )
    }
}
