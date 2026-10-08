package com.example.waterbalancemonitor.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.dashboard.action.DashboardAction
import com.example.waterbalancemonitor.presentation.screens.dashboard.components.AddWaterSection
import com.example.waterbalancemonitor.presentation.screens.dashboard.components.DashboardHeader
import com.example.waterbalancemonitor.presentation.screens.dashboard.components.DrinkPickerSheet
import com.example.waterbalancemonitor.presentation.screens.dashboard.components.ProgressRing
import com.example.waterbalancemonitor.presentation.screens.dashboard.components.TodayIntakeItem
import com.example.waterbalancemonitor.presentation.screens.dashboard.components.VesselPickerSheet
import com.example.waterbalancemonitor.presentation.screens.dashboard.effect.DashboardEffect
import com.example.waterbalancemonitor.presentation.screens.dashboard.state.DashboardState
import com.example.waterbalancemonitor.presentation.screens.dashboard.viewmodel.DashboardViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme

@Composable
fun DashboardScreen(
    onNavigateToGoal: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToGoalEditing -> onNavigateToGoal()
            }
        }
    }

    DashboardContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (state.isDrinkPickerOpen) {
        DrinkPickerSheet(
            drinkTypes = state.drinkTypes,
            selectedId = state.selectedDrinkTypeId,
            onSelect = { viewModel.onAction(DashboardAction.DrinkTypeSelected(it)) },
            onDismiss = { viewModel.onAction(DashboardAction.DismissDrinkPicker) }
        )
    }

    if (state.isVesselPickerOpen) {
        VesselPickerSheet(
            vessels = state.vessels,
            selectedId = state.selectedVesselId,
            onSelect = { viewModel.onAction(DashboardAction.VesselSelected(it)) },
            onDismiss = { viewModel.onAction(DashboardAction.DismissVesselPicker) }
        )
    }
}

@Composable
private fun DashboardContent(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DashboardHeader(
                name = state.name,
                remainingMl = state.remainingMl,
                goalMl = state.goalMl
            )
        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressRing(
                    progress = state.progress,
                    consumedMl = state.consumedMl,
                    goalMl = state.goalMl,
                    percent = state.percent,
                    modifier = Modifier.size(220.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = { onAction(DashboardAction.ChangeGoalClicked) }) {
                    Text(stringResource(R.string.dashboard_change_goal))
                }
            }
        }
        item { AddWaterSection(state = state, onAction = onAction) }
        item {
            Text(
                text = stringResource(R.string.dashboard_today),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        if (state.todayIntakes.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.dashboard_no_intakes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(state.todayIntakes, key = { it.id }) { event ->
                val drinkType = state.drinkTypes.firstOrNull { it.id == event.drinkTypeId }
                val vessel = state.vessels.firstOrNull { it.id == event.vesselId }
                TodayIntakeItem(
                    event = event,
                    drinkType = drinkType,
                    vessel = vessel,
                    onDelete = { onAction(DashboardAction.DeleteIntake(it)) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardContentPreview() {
    WaterBalanceMonitorTheme {
        DashboardContent(
            state = DashboardState(
                name = "Alex",
                consumedMl = 1100,
                goalMl = 2500
            ),
            onAction = {}
        )
    }
}
