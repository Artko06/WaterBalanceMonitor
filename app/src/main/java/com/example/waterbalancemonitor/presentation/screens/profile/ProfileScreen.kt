package com.example.waterbalancemonitor.presentation.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.presentation.screens.profile.action.ProfileAction
import com.example.waterbalancemonitor.presentation.screens.profile.components.EditProfileSheet
import com.example.waterbalancemonitor.presentation.screens.profile.components.ProfileHeader
import com.example.waterbalancemonitor.presentation.screens.profile.components.SettingRow
import com.example.waterbalancemonitor.presentation.screens.profile.components.SettingsSection
import com.example.waterbalancemonitor.presentation.screens.profile.components.SwitchRow
import com.example.waterbalancemonitor.presentation.screens.profile.components.labelRes
import com.example.waterbalancemonitor.presentation.screens.profile.effect.ProfileEffect
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileField
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileState
import com.example.waterbalancemonitor.presentation.screens.profile.viewmodel.ProfileViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToNormInfo: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileEffect.NavigateToNormInfo -> onNavigateToNormInfo()
            }
        }
    }

    ProfileContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (state.editingField != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onAction(ProfileAction.DismissSheet) }
        ) {
            EditProfileSheet(state = state, onAction = viewModel::onAction)
        }
    }
}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ProfileHeader(
            state = state,
            onAction = onAction,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        AboutSection(
            state = state,
            onAction = onAction,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        GoalSection(
            state = state,
            onAction = onAction,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun AboutSection(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsSection(
        title = stringResource(R.string.profile_section_about),
        modifier = modifier
    ) {
        SettingRow(
            label = stringResource(R.string.profile_name),
            value = state.name.ifBlank { stringResource(R.string.value_not_set) },
            onClick = { onAction(ProfileAction.FieldClicked(ProfileField.NAME)) }
        )
        HorizontalDivider()
        SettingRow(
            label = stringResource(R.string.profile_weight),
            value = weightLabel(state),
            onClick = { onAction(ProfileAction.FieldClicked(ProfileField.WEIGHT)) }
        )
        HorizontalDivider()
        SettingRow(
            label = stringResource(R.string.profile_height),
            value = heightLabel(state),
            onClick = { onAction(ProfileAction.FieldClicked(ProfileField.HEIGHT)) }
        )
        HorizontalDivider()
        SettingRow(
            label = stringResource(R.string.profile_age),
            value = ageLabel(state),
            onClick = { onAction(ProfileAction.FieldClicked(ProfileField.AGE)) }
        )
        HorizontalDivider()
        SettingRow(
            label = stringResource(R.string.profile_gender),
            value = stringResource(state.gender.labelRes()),
            onClick = { onAction(ProfileAction.FieldClicked(ProfileField.GENDER)) }
        )
        HorizontalDivider()
        SettingRow(
            label = stringResource(R.string.profile_activity),
            value = stringResource(state.activityLevel.labelRes()),
            onClick = { onAction(ProfileAction.FieldClicked(ProfileField.ACTIVITY)) }
        )
    }
}

@Composable
private fun GoalSection(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsSection(
        title = stringResource(R.string.profile_section_goal),
        modifier = modifier
    ) {
        SwitchRow(
            label = stringResource(R.string.profile_set_manually),
            checked = state.isGoalManual,
            onCheckedChange = { onAction(ProfileAction.ManualGoalToggled(it)) }
        )
        AnimatedVisibility(visible = state.isGoalManual) {
            Column {
                HorizontalDivider()
                SettingRow(
                    label = stringResource(R.string.profile_goal_override),
                    value = "${state.goalOverrideMl} ${stringResource(R.string.unit_ml)}",
                    onClick = { onAction(ProfileAction.FieldClicked(ProfileField.GOAL)) }
                )
            }
        }
    }
}

@Composable
private fun weightLabel(state: ProfileState): String? =
    state.weightKg?.let { "${formatNumber(it)} ${stringResource(R.string.unit_kg)}" }

@Composable
private fun heightLabel(state: ProfileState): String? =
    state.heightCm?.let { "$it ${stringResource(R.string.unit_cm)}" }

@Composable
private fun ageLabel(state: ProfileState): String? =
    state.age?.let { "$it ${stringResource(R.string.unit_years)}" }

private fun formatNumber(value: Double): String =
    if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        String.format(java.util.Locale.US, "%.1f", value)
    }

@Preview(showBackground = true)
@Composable
private fun ProfileContentPreview() {
    WaterBalanceMonitorTheme {
        ProfileContent(
            state = ProfileState(
                name = "Alex",
                weightKg = 72.0,
                heightCm = 178,
                age = 30,
                gender = Gender.MALE,
                activityLevel = ActivityLevel.MODERATE,
                calculatedNormMl = 2510,
                isLoaded = true
            ),
            onAction = {}
        )
    }
}
