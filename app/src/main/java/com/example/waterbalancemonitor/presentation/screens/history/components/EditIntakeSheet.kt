package com.example.waterbalancemonitor.presentation.screens.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.NumberEditor
import com.example.waterbalancemonitor.presentation.mapper.drinkName
import com.example.waterbalancemonitor.presentation.mapper.toDrawableRes
import com.example.waterbalancemonitor.presentation.mapper.vesselName
import com.example.waterbalancemonitor.presentation.screens.history.action.HistoryAction
import com.example.waterbalancemonitor.presentation.screens.history.state.HistoryState
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditIntakeSheet(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    ModalBottomSheet(onDismissRequest = { onAction(HistoryAction.DismissEditor) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.history_edit_title),
                style = MaterialTheme.typography.titleLarge
            )
            PickerRow(
                label = stringResource(R.string.picker_drink_type),
                value = state.editDrinkType?.let { drinkName(it) }.orEmpty(),
                drawableRes = state.editDrinkType?.icon?.toDrawableRes(),
                onEdit = { onAction(HistoryAction.OpenDrinkPicker) }
            )
            PickerRow(
                label = stringResource(R.string.picker_vessel),
                value = state.editVessel?.let {
                    "${vesselName(it)} · ${it.volumeMl} ${stringResource(R.string.unit_ml)}"
                } ?: stringResource(R.string.picker_vessel_none),
                drawableRes = state.editVessel?.icon?.toDrawableRes(),
                onEdit = { onAction(HistoryAction.OpenVesselPicker) }
            )
            TimeRow(
                hour = state.editHour,
                minute = state.editMinute,
                onClick = { showTimePicker = true }
            )
            Text(
                text = stringResource(R.string.history_volume),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            NumberEditor(
                value = state.editVolumeMl.toFloat(),
                range = HistoryState.MIN_VOLUME_ML.toFloat()..HistoryState.MAX_VOLUME_ML.toFloat(),
                step = HistoryState.VOLUME_STEP.toFloat(),
                unit = stringResource(R.string.unit_ml),
                onValueChange = { onAction(HistoryAction.EditVolumeChanged(it.roundToInt())) }
            )
            Text(
                text = stringResource(
                    R.string.history_effective,
                    state.editEffectiveVolume,
                    stringResource(R.string.unit_ml)
                ),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                onClick = { onAction(HistoryAction.SaveEditClicked) },
                enabled = state.editDrinkTypeId != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.history_save))
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            initialHour = state.editHour,
            initialMinute = state.editMinute,
            onConfirm = { hour, minute ->
                onAction(HistoryAction.EditTimeChanged(hour, minute))
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Composable
private fun PickerRow(
    label: String,
    value: String,
    drawableRes: Int?,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onEdit),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (drawableRes != null) {
            Icon(
                painter = painterResource(drawableRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        TextButton(onClick = onEdit) {
            Text(stringResource(R.string.dashboard_change))
        }
    }
}

@Composable
private fun TimeRow(
    hour: Int,
    minute: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.history_time),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "%02d:%02d".format(hour, minute),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        TextButton(onClick = onClick) {
            Text(stringResource(R.string.dashboard_change))
        }
    }
}
