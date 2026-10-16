package com.example.waterbalancemonitor.presentation.screens.achievements.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import com.example.waterbalancemonitor.domain.usecase.achievement.CheckAchievementsUseCase
import com.example.waterbalancemonitor.domain.usecase.achievement.GetAchievementsUseCase
import com.example.waterbalancemonitor.domain.usecase.achievement.ObserveUserAchievementsUseCase
import com.example.waterbalancemonitor.presentation.screens.achievements.action.AchievementsAction
import com.example.waterbalancemonitor.presentation.screens.achievements.buildAchievementItems
import com.example.waterbalancemonitor.presentation.screens.achievements.groupAchievements
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val getAchievements: GetAchievementsUseCase,
    private val observeUserAchievements: ObserveUserAchievementsUseCase,
    private val checkAchievements: CheckAchievementsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AchievementsState())
    val state: StateFlow<AchievementsState> = _state.asStateFlow()

    init {
        viewModelScope.launch { runCatching { checkAchievements() } }
        viewModelScope.launch {
            combine(
                getAchievements(),
                observeUserAchievements()
            ) { achievements, userAchievements ->
                groupAchievements(buildAchievementItems(achievements, userAchievements))
            }.collect { groups ->
                val items = groups.flatMap { it.items }
                _state.update {
                    it.copy(
                        groups = groups,
                        completedCount = items.count { item ->
                            item.status == AchievementStatus.COMPLETED
                        },
                        totalCount = items.size,
                        isLoading = false
                    )
                }
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
