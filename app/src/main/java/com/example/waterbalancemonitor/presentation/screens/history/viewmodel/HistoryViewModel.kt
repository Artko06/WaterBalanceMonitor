package com.example.waterbalancemonitor.presentation.screens.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.usecase.catalog.ObserveDrinkTypesUseCase
import com.example.waterbalancemonitor.domain.usecase.catalog.ObserveVesselsUseCase
import com.example.waterbalancemonitor.domain.usecase.goal.GetDailyGoalUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.DeleteIntakeUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.ObserveIntakesForDayUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.ObserveSummaryForDayUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.UpdateIntakeUseCase
import com.example.waterbalancemonitor.presentation.screens.history.action.HistoryAction
import com.example.waterbalancemonitor.presentation.screens.history.effect.HistoryEffect
import com.example.waterbalancemonitor.presentation.screens.history.state.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val observeDrinkTypes: ObserveDrinkTypesUseCase,
    private val observeVessels: ObserveVesselsUseCase,
    private val observeIntakesForDay: ObserveIntakesForDayUseCase,
    private val observeSummaryForDay: ObserveSummaryForDayUseCase,
    private val getDailyGoal: GetDailyGoalUseCase,
    private val updateIntake: UpdateIntakeUseCase,
    private val deleteIntake: DeleteIntakeUseCase
) : ViewModel() {

    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    private val selectedDate = MutableStateFlow(today)

    private val _state = MutableStateFlow(HistoryState(selectedDate = today))
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HistoryEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: Flow<HistoryEffect> = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            observeDrinkTypes().collect { types ->
                _state.update { it.copy(drinkTypes = types) }
            }
        }
        viewModelScope.launch {
            observeVessels().collect { vessels ->
                _state.update { it.copy(vessels = vessels) }
            }
        }
        viewModelScope.launch {
            selectedDate
                .flatMapLatest { date ->
                    combine(
                        observeIntakesForDay(date),
                        observeSummaryForDay(date),
                        getDailyGoal(date)
                    ) { events, summary, goal -> DayData(date, events, summary, goal) }
                }
                .collect { data ->
                    val totalMl = data.summary?.totalMl ?: 0
                    val targetMl = data.goal?.targetMl ?: 0
                    _state.update {
                        it.copy(
                            selectedDate = data.date,
                            isToday = data.date == today,
                            events = data.events,
                            dayTotalMl = totalMl,
                            goalTargetMl = targetMl,
                            isCompleted = data.goal?.isCompleted
                                ?: (targetMl > 0 && totalMl >= targetMl),
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            HistoryAction.PreviousDay -> setSelectedDate(_state.value.selectedDate.minusDay())
            HistoryAction.NextDay -> setSelectedDate(_state.value.selectedDate.plusDay())
            HistoryAction.TodayClicked -> setSelectedDate(today)
            is HistoryAction.DateSelected -> setSelectedDate(action.date)
            is HistoryAction.OpenEditor -> openEditor(action.event)
            HistoryAction.DismissEditor -> _state.update { it.copy(editingEvent = null) }
            HistoryAction.OpenDrinkPicker ->
                _state.update { it.copy(isDrinkPickerOpen = true) }
            HistoryAction.DismissDrinkPicker ->
                _state.update { it.copy(isDrinkPickerOpen = false) }
            HistoryAction.OpenVesselPicker ->
                _state.update { it.copy(isVesselPickerOpen = true) }
            HistoryAction.DismissVesselPicker ->
                _state.update { it.copy(isVesselPickerOpen = false) }
            is HistoryAction.EditDrinkTypeSelected ->
                _state.update { it.copy(editDrinkTypeId = action.id, isDrinkPickerOpen = false) }
            is HistoryAction.EditVesselSelected ->
                _state.update { it.copy(editVesselId = action.id, isVesselPickerOpen = false) }
            is HistoryAction.EditVolumeChanged ->
                _state.update {
                    it.copy(
                        editVolumeMl = action.ml
                            .coerceIn(HistoryState.MIN_VOLUME_ML, HistoryState.MAX_VOLUME_ML)
                    )
                }
            is HistoryAction.EditTimeChanged ->
                _state.update {
                    it.copy(
                        editHour = action.hour.coerceIn(0, 23),
                        editMinute = action.minute.coerceIn(0, 59)
                    )
                }
            HistoryAction.SaveEditClicked -> saveEdit()
            is HistoryAction.DeleteIntake -> viewModelScope.launch {
                runCatching { deleteIntake(action.event) }
                    .onFailure { _effect.tryEmit(HistoryEffect.ShowSaveError) }
            }
        }
    }

    private fun setSelectedDate(date: LocalDate) {
        val clamped = if (date > today) today else date
        selectedDate.value = clamped
        _state.update { it.copy(selectedDate = clamped, isToday = clamped == today) }
    }

    private fun openEditor(event: IntakeEvent) {
        _state.update {
            it.copy(
                editingEvent = event,
                editDrinkTypeId = event.drinkTypeId,
                editVesselId = event.vesselId,
                editVolumeMl = event.volumeMl,
                editHour = event.consumedAt.hour,
                editMinute = event.consumedAt.minute,
                isDrinkPickerOpen = false,
                isVesselPickerOpen = false
            )
        }
    }

    private fun saveEdit() {
        val current = _state.value
        val event = current.editingEvent ?: return
        val drinkTypeId = current.editDrinkTypeId ?: return
        viewModelScope.launch {
            runCatching {
                updateIntake(
                    event.copy(
                        drinkTypeId = drinkTypeId,
                        vesselId = current.editVesselId,
                        volumeMl = current.editVolumeMl,
                        consumedAt = LocalDateTime(
                            current.selectedDate,
                            LocalTime(current.editHour, current.editMinute)
                        )
                    )
                )
            }.onFailure { _effect.tryEmit(HistoryEffect.ShowSaveError) }
            _state.update { it.copy(editingEvent = null) }
        }
    }

    private fun LocalDate.minusDay(): LocalDate = this.plus(DatePeriod(days = -1))

    private fun LocalDate.plusDay(): LocalDate = this.plus(DatePeriod(days = 1))

    private data class DayData(
        val date: LocalDate,
        val events: List<IntakeEvent>,
        val summary: DailySummary?,
        val goal: DailyGoal?
    )
}
