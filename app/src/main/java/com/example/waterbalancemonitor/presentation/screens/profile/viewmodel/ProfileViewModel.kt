package com.example.waterbalancemonitor.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.usecase.profile.CalculateDailyNormUseCase
import com.example.waterbalancemonitor.domain.usecase.profile.ObserveProfileUseCase
import com.example.waterbalancemonitor.domain.usecase.profile.SaveProfileUseCase
import com.example.waterbalancemonitor.presentation.screens.profile.action.ProfileAction
import com.example.waterbalancemonitor.presentation.screens.profile.effect.ProfileEffect
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileField
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.time.Clock

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeProfile: ObserveProfileUseCase,
    private val saveProfile: SaveProfileUseCase,
    private val calculateDailyNorm: CalculateDailyNormUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: Flow<ProfileEffect> = _effect.asSharedFlow()

    private var profileId: Long = 1L

    init {
        viewModelScope.launch {
            val profile = observeProfile().first()
            if (profile != null) {
                profileId = profile.id
                val calculated = calculateDailyNorm(
                    profile.weightKg,
                    profile.gender,
                    profile.activityLevel
                )
                val manual = profile.dailyNormMl != 0 && profile.dailyNormMl != calculated
                update {
                    it.copy(
                        name = profile.name,
                        weightKg = profile.weightKg,
                        heightCm = profile.heightCm?.roundToInt(),
                        age = profile.age,
                        gender = profile.gender,
                        activityLevel = profile.activityLevel,
                        isGoalManual = manual,
                        goalOverrideMl = if (manual) profile.dailyNormMl else null,
                        isLoaded = true
                    )
                }
            } else {
                _state.update { it.copy(isLoaded = true) }
            }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.FieldClicked -> openField(action.field)

            ProfileAction.DismissSheet ->
                _state.update { it.copy(editingField = null, showValidation = false) }

            is ProfileAction.DraftTextChanged ->
                _state.update { it.copy(draftText = action.value) }

            is ProfileAction.DraftNumberChanged ->
                _state.update { it.copy(draftNumber = action.value) }

            is ProfileAction.GenderSelected -> {
                update { it.copy(gender = action.gender, editingField = null) }
                persistIfValid()
            }

            is ProfileAction.ActivitySelected -> {
                update { it.copy(activityLevel = action.activityLevel, editingField = null) }
                persistIfValid()
            }

            is ProfileAction.ManualGoalToggled -> {
                _state.update { current ->
                    current.copy(
                        isGoalManual = action.enabled,
                        goalOverrideMl = if (action.enabled) {
                            current.goalOverrideMl ?: current.calculatedNormMl.takeIf { it > 0 }
                        } else {
                            current.goalOverrideMl
                        }
                    )
                }
                persistIfValid()
            }

            ProfileAction.ApplyClicked -> applyDraft()

            ProfileAction.NormInfoClicked ->
                _effect.tryEmit(ProfileEffect.NavigateToNormInfo)
        }
    }

    private fun openField(field: ProfileField) {
        val current = _state.value
        val draftNumber = when (field) {
            ProfileField.WEIGHT -> (current.weightKg?.toFloat() ?: DEFAULT_WEIGHT)
            ProfileField.HEIGHT -> (current.heightCm?.toFloat() ?: DEFAULT_HEIGHT)
            ProfileField.AGE -> (current.age?.toFloat() ?: DEFAULT_AGE)
            ProfileField.GOAL -> (current.goalOverrideMl?.toFloat()
                ?: current.calculatedNormMl.takeIf { it > 0 }?.toFloat()
                ?: DEFAULT_GOAL)
            else -> null
        }
        _state.update {
            it.copy(
                editingField = field,
                draftText = if (field == ProfileField.NAME) current.name else "",
                draftNumber = draftNumber,
                showValidation = false
            )
        }
    }

    private fun applyDraft() {
        val current = _state.value
        val field = current.editingField ?: return

        when (field) {
            ProfileField.NAME -> commit(current.copy(name = current.draftText.trim()))

            ProfileField.WEIGHT -> {
                val value = current.draftNumber ?: return
                if (value < ProfileState.MIN_WEIGHT || value > ProfileState.MAX_WEIGHT) return
                commit(current.copy(weightKg = value.toDouble()))
            }

            ProfileField.HEIGHT -> {
                val value = current.draftNumber?.roundToInt() ?: return
                if (value < ProfileState.MIN_HEIGHT || value > ProfileState.MAX_HEIGHT) return
                commit(current.copy(heightCm = value))
            }

            ProfileField.AGE -> {
                val value = current.draftNumber?.roundToInt() ?: return
                if (value < ProfileState.MIN_AGE || value > ProfileState.MAX_AGE) return
                commit(current.copy(age = value))
            }

            ProfileField.GOAL -> {
                val value = current.draftNumber?.roundToInt() ?: return
                if (value < ProfileState.MIN_GOAL || value > ProfileState.MAX_GOAL) return
                commit(current.copy(goalOverrideMl = value))
            }

            ProfileField.GENDER, ProfileField.ACTIVITY -> Unit
        }
    }

    private fun commit(updated: ProfileState) {
        _state.update {
            recompute(updated.copy(editingField = null, showValidation = false))
        }
        persistIfValid()
    }

    private fun recompute(current: ProfileState): ProfileState {
        val weight = current.weightKg
        val norm = if (weight != null && weight > 0.0) {
            calculateDailyNorm(weight, current.gender, current.activityLevel)
        } else {
            0
        }
        return current.copy(calculatedNormMl = norm)
    }

    private fun persistIfValid() {
        val current = _state.value
        val weight = current.weightKg ?: return
        if (!current.isValid) return
        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            saveProfile(
                Profile(
                    id = profileId,
                    name = current.name.trim(),
                    weightKg = weight,
                    heightCm = current.heightCm?.toDouble(),
                    age = current.age,
                    gender = current.gender,
                    activityLevel = current.activityLevel,
                    dailyNormMl = current.effectiveNormMl,
                    updatedAt = now
                )
            )
        }
    }

    private fun update(reducer: (ProfileState) -> ProfileState) {
        _state.update { recompute(reducer(it)) }
    }

    private companion object {
        const val DEFAULT_WEIGHT = 70f
        const val DEFAULT_HEIGHT = 170f
        const val DEFAULT_AGE = 30f
        const val DEFAULT_GOAL = 2000f
    }
}
