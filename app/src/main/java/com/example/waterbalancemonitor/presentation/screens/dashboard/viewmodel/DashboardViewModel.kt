package com.example.waterbalancemonitor.presentation.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.usecase.catalog.ObserveDrinkTypesUseCase
import com.example.waterbalancemonitor.domain.usecase.catalog.ObserveVesselsUseCase
import com.example.waterbalancemonitor.domain.usecase.goal.EnsureTodayGoalUseCase
import com.example.waterbalancemonitor.domain.usecase.goal.ObserveTodayGoalUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.DeleteIntakeUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.GetTodaySummaryUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.LogIntakeUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.ObserveTodayIntakesUseCase
import com.example.waterbalancemonitor.domain.usecase.profile.ObserveProfileUseCase
import com.example.waterbalancemonitor.presentation.screens.dashboard.action.DashboardAction
import com.example.waterbalancemonitor.presentation.screens.dashboard.effect.DashboardEffect
import com.example.waterbalancemonitor.presentation.screens.dashboard.state.DashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class DashboardViewModel @Inject constructor(
    observeProfile: ObserveProfileUseCase,
    observeTodaySummary: GetTodaySummaryUseCase,
    observeTodayGoal: ObserveTodayGoalUseCase,
    observeTodayIntakes: ObserveTodayIntakesUseCase,
    observeDrinkTypes: ObserveDrinkTypesUseCase,
    observeVessels: ObserveVesselsUseCase,
    private val logIntake: LogIntakeUseCase,
    private val deleteIntake: DeleteIntakeUseCase,
    private val ensureTodayGoal: EnsureTodayGoalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DashboardEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: Flow<DashboardEffect> = _effect.asSharedFlow()

    init {
        viewModelScope.launch { runCatching { ensureTodayGoal() } }

        viewModelScope.launch {
            observeProfile().collect { profile ->
                _state.update { it.copy(name = profile?.name.orEmpty()) }
            }
        }
        viewModelScope.launch {
            observeTodaySummary().collect { summary ->
                _state.update { it.copy(consumedMl = summary?.totalMl ?: 0) }
            }
        }
        viewModelScope.launch {
            observeTodayGoal().collect { goal ->
                _state.update { it.copy(goalMl = goal?.targetMl ?: 0) }
            }
        }
        viewModelScope.launch {
            observeTodayIntakes().collect { events ->
                _state.update { it.copy(todayIntakes = events) }
            }
        }
        viewModelScope.launch {
            observeDrinkTypes().collect { types ->
                _state.update { current ->
                    val selected = current.selectedDrinkTypeId
                        ?: types.firstOrNull { it.isDefault }?.id
                        ?: types.firstOrNull()?.id
                    current.copy(drinkTypes = types, selectedDrinkTypeId = selected)
                }
            }
        }
        viewModelScope.launch {
            observeVessels().collect { vessels ->
                _state.update { it.copy(vessels = vessels) }
            }
        }
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.ToggleAddPanel ->
                _state.update { it.copy(isAddPanelVisible = !it.isAddPanelVisible) }

            is DashboardAction.DrinkTypeSelected ->
                _state.update { it.copy(selectedDrinkTypeId = action.id, isDrinkPickerOpen = false) }

            DashboardAction.OpenDrinkPicker ->
                _state.update { it.copy(isDrinkPickerOpen = true) }

            DashboardAction.DismissDrinkPicker ->
                _state.update { it.copy(isDrinkPickerOpen = false) }

            DashboardAction.OpenVesselPicker ->
                _state.update { it.copy(isVesselPickerOpen = true) }

            DashboardAction.DismissVesselPicker ->
                _state.update { it.copy(isVesselPickerOpen = false) }

            is DashboardAction.VesselSelected -> {
                val vessel = action.id?.let { id -> _state.value.vessels.firstOrNull { it.id == id } }
                _state.update {
                    it.copy(
                        selectedVesselId = action.id,
                        volumeMl = vessel?.volumeMl ?: it.volumeMl,
                        isVesselPickerOpen = false
                    )
                }
            }

            is DashboardAction.VolumeChanged ->
                _state.update { it.copy(volumeMl = action.volumeMl) }

            DashboardAction.AddClicked -> addIntake()

            is DashboardAction.DeleteIntake ->
                viewModelScope.launch { deleteIntake(action.event) }

            DashboardAction.ChangeGoalClicked ->
                _effect.tryEmit(DashboardEffect.NavigateToGoalEditing)
        }
    }

    private fun addIntake() {
        val current = _state.value
        val drinkTypeId = current.selectedDrinkTypeId ?: return
        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            logIntake(
                IntakeEvent(
                    id = 0,
                    drinkTypeId = drinkTypeId,
                    vesselId = current.selectedVesselId,
                    volumeMl = current.volumeMl,
                    consumedAt = now
                )
            )
        }
    }
}
