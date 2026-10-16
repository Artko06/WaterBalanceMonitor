package com.example.waterbalancemonitor.presentation.screens.achievements.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.usecase.achievement.CheckAchievementsUseCase
import com.example.waterbalancemonitor.domain.usecase.achievement.GetAchievementsUseCase
import com.example.waterbalancemonitor.domain.usecase.achievement.ObserveUserAchievementsUseCase
import com.example.waterbalancemonitor.presentation.screens.achievements.action.AchievementsAction
import com.example.waterbalancemonitor.presentation.screens.achievements.buildAchievementItems
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementGroupViewModel @Inject constructor(
    private val getAchievements: GetAchievementsUseCase,
    private val observeUserAchievements: ObserveUserAchievementsUseCase,
    private val checkAchievements: CheckAchievementsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AchievementGroupState())
    val state: StateFlow<AchievementGroupState> = _state.asStateFlow()

    init {
        viewModelScope.launch { runCatching { checkAchievements() } }
        viewModelScope.launch {
            combine(
                getAchievements(),
                observeUserAchievements()
            ) { achievements, userAchievements ->
                buildAchievementItems(achievements, userAchievements)
            }.collect { items ->
                _state.update { it.copy(items = items, isLoading = false) }
            }
        }
    }

    fun onAction(action: AchievementsAction) {
        when (action) {
            AchievementsAction.Refresh -> viewModelScope.launch {
                runCatching { checkAchievements() }
            }
        }
    }
}
