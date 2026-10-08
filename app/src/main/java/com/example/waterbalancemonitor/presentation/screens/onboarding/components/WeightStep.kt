package com.example.waterbalancemonitor.presentation.screens.onboarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.NumberEditor
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingState

@Composable
fun WeightStep(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    OnboardingStepContent(
        title = stringResource(R.string.onboarding_weight_title),
        description = stringResource(R.string.onboarding_weight_desc)
    ) {
        NumberEditor(
            value = state.weightKg,
            range = OnboardingState.MIN_WEIGHT..OnboardingState.MAX_WEIGHT,
            step = OnboardingState.WEIGHT_STEP,
            unit = stringResource(R.string.unit_kg),
            onValueChange = { onAction(OnboardingAction.WeightChanged(it)) }
        )
    }
}
