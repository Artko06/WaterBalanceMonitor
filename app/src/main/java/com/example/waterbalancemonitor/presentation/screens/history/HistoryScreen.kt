package com.example.waterbalancemonitor.presentation.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.presentation.components.DrinkPickerSheet
import com.example.waterbalancemonitor.presentation.components.IntakeListItem
import com.example.waterbalancemonitor.presentation.components.VesselPickerSheet
import com.example.waterbalancemonitor.presentation.components.rememberEnterAnimation
import com.example.waterbalancemonitor.presentation.screens.history.action.HistoryAction
import com.example.waterbalancemonitor.presentation.screens.history.components.DayNavigator
import com.example.waterbalancemonitor.presentation.screens.history.components.DaySummaryCard
import com.example.waterbalancemonitor.presentation.screens.history.components.EditIntakeSheet
import com.example.waterbalancemonitor.presentation.screens.history.effect.HistoryEffect
import com.example.waterbalancemonitor.presentation.screens.history.state.HistoryState
import com.example.waterbalancemonitor.presentation.screens.history.viewmodel.HistoryViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(R.string.history_save_error)
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HistoryEffect.ShowSaveError -> snackbarHostState.showSnackbar(errorMessage)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        HistoryContent(
            state = state,
            onAction = viewModel::onAction,
            onPickDate = { showDatePicker = true }
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.selectedDate.toUtcMillis()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            viewModel.onAction(
                                HistoryAction.DateSelected(millis.toLocalDateUtc())
                            )
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.action_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (state.isEditing) {
        EditIntakeSheet(state = state, onAction = viewModel::onAction)
    }

    if (state.isDrinkPickerOpen) {
        DrinkPickerSheet(
            drinkTypes = state.drinkTypes,
            selectedId = state.editDrinkTypeId,
            onSelect = { viewModel.onAction(HistoryAction.EditDrinkTypeSelected(it)) },
            onDismiss = { viewModel.onAction(HistoryAction.DismissDrinkPicker) }
        )
    }

    if (state.isVesselPickerOpen) {
        VesselPickerSheet(
            vessels = state.vessels,
            selectedId = state.editVesselId,
            onSelect = { viewModel.onAction(HistoryAction.EditVesselSelected(it)) },
            onDismiss = { viewModel.onAction(HistoryAction.DismissVesselPicker) }
        )
    }
}

@Composable
private fun HistoryContent(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
    onPickDate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val entered = rememberEnterAnimation()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            DayNavigator(
                date = state.selectedDate,
                isToday = state.isToday,
                onPrevious = { onAction(HistoryAction.PreviousDay) },
                onNext = { onAction(HistoryAction.NextDay) },
                onPickDate = onPickDate,
                onToday = { onAction(HistoryAction.TodayClicked) }
            )
        }
        item {
            DaySummaryCard(state = state, entered = entered)
        }
        if (state.events.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.history_no_intakes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        } else {
            items(state.events, key = { it.id }) { event ->
                IntakeListItem(
                    event = event,
                    drinkType = state.drinkTypes.firstOrNull { it.id == event.drinkTypeId },
                    vessel = state.vessels.firstOrNull { it.id == event.vesselId },
                    onDelete = { onAction(HistoryAction.DeleteIntake(it)) },
                    modifier = Modifier.animateItem(),
                    onEdit = { onAction(HistoryAction.OpenEditor(it)) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryContentPreview() {
    WaterBalanceMonitorTheme {
        HistoryContent(
            state = HistoryState(
                selectedDate = LocalDate(2026, 10, 15),
                isToday = true,
                events = listOf(
                    IntakeEvent(1, 1, 1, 250, LocalDateTime(2026, 10, 15, 9, 30)),
                    IntakeEvent(2, 2, 2, 300, LocalDateTime(2026, 10, 15, 12, 15))
                ),
                dayTotalMl = 520,
                goalTargetMl = 2500,
                isCompleted = false,
                isLoading = false
            ),
            onAction = {},
            onPickDate = {}
        )
    }
}

private fun LocalDate.toUtcMillis(): Long =
    atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()

private fun Long.toLocalDateUtc(): LocalDate =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC).date
