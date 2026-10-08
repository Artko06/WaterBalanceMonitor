package com.example.waterbalancemonitor.presentation.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.components.ActivityStep
import com.example.waterbalancemonitor.presentation.screens.onboarding.components.GenderStep
import com.example.waterbalancemonitor.presentation.screens.onboarding.components.SummaryStep
import com.example.waterbalancemonitor.presentation.screens.onboarding.components.WeightStep
import com.example.waterbalancemonitor.presentation.screens.onboarding.components.WelcomeStep
import com.example.waterbalancemonitor.presentation.screens.onboarding.effect.OnboardingEffect
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingStep
import com.example.waterbalancemonitor.presentation.screens.onboarding.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    onNavigateToNormInfo: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingEffect.Finished -> onFinished()
                OnboardingEffect.NavigateToNormInfo -> onNavigateToNormInfo()
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        LinearProgressIndicator(
            progress = { state.progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )
        AnimatedContent(
            targetState = state.step,
            label = "onboardingStep",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { step ->
            when (step) {
                OnboardingStep.WELCOME -> WelcomeStep(state, viewModel::onAction)
                OnboardingStep.WEIGHT -> WeightStep(state, viewModel::onAction)
                OnboardingStep.GENDER -> GenderStep(state, viewModel::onAction)
                OnboardingStep.ACTIVITY -> ActivityStep(state, viewModel::onAction)
                OnboardingStep.SUMMARY -> SummaryStep(state, viewModel::onAction)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!state.isFirstStep) {
                OutlinedButton(
                    onClick = { viewModel.onAction(OnboardingAction.BackClicked) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.onboarding_back))
                }
            }
            Button(
                onClick = {
                    viewModel.onAction(
                        if (state.isLastStep) {
                            OnboardingAction.FinishClicked
                        } else {
                            OnboardingAction.NextClicked
                        }
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        if (state.isLastStep) R.string.onboarding_start else R.string.onboarding_next
                    )
                )
            }
        }
    }
}
