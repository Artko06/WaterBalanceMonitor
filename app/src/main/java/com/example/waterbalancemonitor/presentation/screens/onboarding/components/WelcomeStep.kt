package com.example.waterbalancemonitor.presentation.screens.onboarding.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingState

@Composable
fun WelcomeStep(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    OnboardingStepContent(
        title = stringResource(R.string.onboarding_welcome_title),
        description = stringResource(R.string.onboarding_welcome_desc)
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onAction(OnboardingAction.NameChanged(it)) },
            label = { Text(stringResource(R.string.onboarding_name_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
