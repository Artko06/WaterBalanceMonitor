package com.example.waterbalancemonitor.presentation.screens.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.NumberEditor
import com.example.waterbalancemonitor.presentation.mapper.toDrawableRes
import com.example.waterbalancemonitor.presentation.screens.dashboard.action.DashboardAction
import com.example.waterbalancemonitor.presentation.screens.dashboard.state.DashboardState
import kotlin.math.roundToInt

@Composable
fun AddWaterSection(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction(DashboardAction.ToggleAddPanel) }
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dashboard_add_water),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (state.isAddPanelVisible) {
                        Icons.Filled.KeyboardArrowUp
                    } else {
                        Icons.Filled.KeyboardArrowDown
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AnimatedVisibility(visible = state.isAddPanelVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_drink_type),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    state.selectedDrinkType?.let { drinkType ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(drinkType.icon.toDrawableRes()),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                text = drinkType.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp)
                            )
                            TextButton(onClick = { onAction(DashboardAction.OpenDrinkPicker) }) {
                                Text(stringResource(R.string.dashboard_change))
                            }
                        }
                    }
                    Text(
                        text = stringResource(R.string.dashboard_vessel),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val selectedVessel = state.vessels.firstOrNull {
                            it.id == state.selectedVesselId
                        }
                        if (selectedVessel != null) {
                            Icon(
                                painter = painterResource(selectedVessel.icon.toDrawableRes()),
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
                        Text(
                            text = selectedVessel?.let {
                                "${it.name} · ${it.volumeMl} ${stringResource(R.string.unit_ml)}"
                            } ?: stringResource(R.string.dashboard_vessel_none),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        )
                        TextButton(onClick = { onAction(DashboardAction.OpenVesselPicker) }) {
                            Text(stringResource(R.string.dashboard_change))
                        }
                    }
                    Text(
                        text = stringResource(R.string.dashboard_volume),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    NumberEditor(
                        value = state.volumeMl.toFloat(),
                        range = DashboardState.MIN_VOLUME_ML.toFloat()..DashboardState.MAX_VOLUME_ML.toFloat(),
                        step = DashboardState.VOLUME_STEP.toFloat(),
                        unit = stringResource(R.string.unit_ml),
                        onValueChange = { onAction(DashboardAction.VolumeChanged(it.roundToInt())) }
                    )
                    Text(
                        text = stringResource(
                            R.string.dashboard_effective,
                            state.effectiveVolume,
                            stringResource(R.string.unit_ml)
                        ),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Button(
                        onClick = { onAction(DashboardAction.AddClicked) },
                        enabled = state.selectedDrinkTypeId != null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.dashboard_add))
                    }
                }
            }
        }
    }
}
