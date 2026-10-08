package com.example.waterbalancemonitor.presentation.screens.onboarding.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingState

@Composable
fun SummaryStep(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    OnboardingStepContent(
        title = stringResource(R.string.onboarding_summary_title),
        description = stringResource(R.string.onboarding_summary_desc)
    ) {
        val animatedNorm by animateIntAsState(
            targetValue = state.calculatedNormMl,
            label = "onboardingNorm"
        )
        Text(
            text = "$animatedNorm ${stringResource(R.string.unit_ml)}",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = { onAction(OnboardingAction.NormInfoClicked) }) {
            Text(stringResource(R.string.onboarding_how))
        }
    }
}
