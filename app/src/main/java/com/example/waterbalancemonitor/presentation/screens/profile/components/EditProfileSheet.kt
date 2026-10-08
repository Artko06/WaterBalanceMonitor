package com.example.waterbalancemonitor.presentation.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.presentation.screens.profile.action.ProfileAction
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileField
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileState

@Composable
fun EditProfileSheet(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    val field = state.editingField ?: return
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(field.titleRes()),
            style = MaterialTheme.typography.titleLarge
        )
        when (field) {
            ProfileField.NAME -> NameEditor(state = state, onAction = onAction)

            ProfileField.WEIGHT -> NumberEditor(
                value = state.draftNumber ?: DEFAULT_WEIGHT,
                range = ProfileState.MIN_WEIGHT.toFloat()..ProfileState.MAX_WEIGHT.toFloat(),
                step = ProfileState.WEIGHT_STEP,
                unit = stringResource(R.string.unit_kg),
                onValueChange = { onAction(ProfileAction.DraftNumberChanged(it)) },
                onApply = { onAction(ProfileAction.ApplyClicked) }
            )

            ProfileField.HEIGHT -> NumberEditor(
                value = state.draftNumber ?: DEFAULT_HEIGHT,
                range = ProfileState.MIN_HEIGHT.toFloat()..ProfileState.MAX_HEIGHT.toFloat(),
                step = ProfileState.HEIGHT_STEP,
                unit = stringResource(R.string.unit_cm),
                onValueChange = { onAction(ProfileAction.DraftNumberChanged(it)) },
                onApply = { onAction(ProfileAction.ApplyClicked) }
            )

            ProfileField.AGE -> NumberEditor(
                value = state.draftNumber ?: DEFAULT_AGE,
                range = ProfileState.MIN_AGE.toFloat()..ProfileState.MAX_AGE.toFloat(),
                step = ProfileState.AGE_STEP,
                unit = stringResource(R.string.unit_years),
                onValueChange = { onAction(ProfileAction.DraftNumberChanged(it)) },
                onApply = { onAction(ProfileAction.ApplyClicked) }
            )

            ProfileField.GOAL -> NumberEditor(
                value = state.draftNumber ?: DEFAULT_GOAL,
                range = ProfileState.MIN_GOAL.toFloat()..ProfileState.MAX_GOAL.toFloat(),
                step = ProfileState.GOAL_STEP,
                unit = stringResource(R.string.unit_ml),
                onValueChange = { onAction(ProfileAction.DraftNumberChanged(it)) },
                onApply = { onAction(ProfileAction.ApplyClicked) }
            )

            ProfileField.GENDER -> GenderEditor(state = state, onAction = onAction)

            ProfileField.ACTIVITY -> ActivityEditor(state = state, onAction = onAction)
        }
    }
}

@Composable
private fun NameEditor(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    OutlinedTextField(
        value = state.draftText,
        onValueChange = { onAction(ProfileAction.DraftTextChanged(it)) },
        label = { Text(stringResource(R.string.profile_name)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        isError = state.showValidation,
        supportingText = if (state.showValidation) {
            { Text(stringResource(R.string.error_name_required)) }
        } else {
            null
        },
        modifier = Modifier.fillMaxWidth()
    )
    Button(
        onClick = { onAction(ProfileAction.ApplyClicked) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.sheet_save))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenderEditor(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Gender.entries.forEach { gender ->
            FilterChip(
                selected = state.gender == gender,
                onClick = { onAction(ProfileAction.GenderSelected(gender)) },
                label = { Text(stringResource(gender.labelRes())) }
            )
        }
    }
}

@Composable
private fun ActivityEditor(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    Column(modifier = Modifier.selectableGroup()) {
        ActivityLevel.entries.forEach { level ->
            val selected = state.activityLevel == level
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selected,
                        onClick = { onAction(ProfileAction.ActivitySelected(level)) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selected, onClick = null)
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = stringResource(level.labelRes()),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(level.descriptionRes()),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private const val DEFAULT_WEIGHT = 70f
private const val DEFAULT_HEIGHT = 170f
private const val DEFAULT_AGE = 30f
private const val DEFAULT_GOAL = 2000f
