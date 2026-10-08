package com.example.waterbalancemonitor.presentation.screens.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.usecase.profile.CalculateDailyNormUseCase
import com.example.waterbalancemonitor.domain.usecase.profile.SaveProfileUseCase
import com.example.waterbalancemonitor.presentation.screens.onboarding.action.OnboardingAction
import com.example.waterbalancemonitor.presentation.screens.onboarding.effect.OnboardingEffect
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingState
import com.example.waterbalancemonitor.presentation.screens.onboarding.state.OnboardingStep
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
class OnboardingViewModel @Inject constructor(
    private val calculateDailyNorm: CalculateDailyNormUseCase,
    private val saveProfile: SaveProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(recompute(OnboardingState()))
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<OnboardingEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: Flow<OnboardingEffect> = _effect.asSharedFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.WeightChanged -> update { it.copy(weightKg = action.value) }

            is OnboardingAction.GenderSelected -> update { it.copy(gender = action.gender) }

            is OnboardingAction.ActivitySelected -> update { it.copy(activityLevel = action.activityLevel) }

            is OnboardingAction.NameChanged -> _state.update { it.copy(name = action.value) }

            OnboardingAction.NextClicked -> move(forward = true)

            OnboardingAction.BackClicked -> move(forward = false)

            OnboardingAction.FinishClicked -> finish()

            OnboardingAction.NormInfoClicked -> _effect.tryEmit(OnboardingEffect.NavigateToNormInfo)
        }
    }

    private fun update(reducer: (OnboardingState) -> OnboardingState) {
        _state.update { recompute(reducer(it)) }
    }

    private fun recompute(current: OnboardingState): OnboardingState = current.copy(
        calculatedNormMl = calculateDailyNorm(
            current.weightKg.toDouble(),
            current.gender,
            current.activityLevel
        )
    )

    private fun move(forward: Boolean) {
        val steps = OnboardingStep.entries
        val index = _state.value.step.ordinal
        val target = if (forward) index + 1 else index - 1
        if (target in steps.indices) {
            _state.update { it.copy(step = steps[target]) }
        }
    }

    private fun finish() {
        viewModelScope.launch {
            val current = _state.value
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            saveProfile(
                Profile(
                    id = 1,
                    name = current.name.trim(),
                    weightKg = current.weightKg.toDouble(),
                    heightCm = null,
                    age = null,
                    gender = current.gender,
                    activityLevel = current.activityLevel,
                    dailyNormMl = current.calculatedNormMl,
                    updatedAt = now
                )
            )
            _effect.tryEmit(OnboardingEffect.Finished)
        }
    }
}
