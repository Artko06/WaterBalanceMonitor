package com.example.waterbalancemonitor.presentation.screens.goals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.usecase.goal.ObserveGoalsForRangeUseCase
import com.example.waterbalancemonitor.domain.usecase.goal.ObserveTodayGoalUseCase
import com.example.waterbalancemonitor.domain.usecase.goal.SetGoalBonusUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.GetSummaryForRangeUseCase
import com.example.waterbalancemonitor.domain.usecase.intake.GetTodaySummaryUseCase
import com.example.waterbalancemonitor.domain.usecase.profile.ObserveProfileUseCase
import com.example.waterbalancemonitor.presentation.screens.goals.action.GoalsAction
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalHistoryDay
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val observeProfile: ObserveProfileUseCase,
    private val observeTodayGoal: ObserveTodayGoalUseCase,
    private val observeTodaySummary: GetTodaySummaryUseCase,
    private val observeGoalsForRange: ObserveGoalsForRangeUseCase,
    private val observeSummariesForRange: GetSummaryForRangeUseCase,
    private val setGoalBonus: SetGoalBonusUseCase
) : ViewModel() {

    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    private val _state = MutableStateFlow(GoalsState())
    val state: StateFlow<GoalsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeProfile().collect { profile ->
                _state.update { it.copy(name = profile?.name.orEmpty()) }
            }
        }
        viewModelScope.launch {
            observeTodayGoal().collect { goal ->
                _state.update {
                    it.copy(
                        baseNormMl = goal?.baseNormMl ?: 0,
                        bonusMl = goal?.bonusMl ?: 0,
                        targetMl = goal?.targetMl ?: 0,
                        isLoading = false
                    )
                }
            }
        }
        viewModelScope.launch {
            observeTodaySummary().collect { summary ->
                _state.update { it.copy(consumedMl = summary?.totalMl ?: 0) }
            }
        }
        viewModelScope.launch {
            val from = today.minus(DatePeriod(days = HISTORY_DAYS))
            combine(
                observeTodayGoal(),
                observeGoalsForRange(from, today),
                observeSummariesForRange(from, today)
            ) { goal, goals, summaries ->
                buildHistory(from, goals, summaries, goal?.baseNormMl ?: 0)
            }.collect { history ->
                _state.update { it.copy(history = history) }
            }
        }
    }

    fun onAction(action: GoalsAction) {
        when (action) {
            GoalsAction.OpenBonusEditor ->
                _state.update { it.copy(isEditBonusOpen = true, bonusDraftMl = it.bonusMl) }

            GoalsAction.DismissBonusEditor ->
                _state.update { it.copy(isEditBonusOpen = false) }

            is GoalsAction.BonusChanged ->
                _state.update {
                    it.copy(
                        bonusDraftMl = action.ml
                            .coerceIn(GoalsState.MIN_BONUS_ML, GoalsState.MAX_BONUS_ML)
                    )
                }

            GoalsAction.SaveBonusClicked -> saveBonus()
        }
    }

    private fun saveBonus() {
        val bonus = _state.value.bonusDraftMl
        viewModelScope.launch {
            runCatching { setGoalBonus(today, bonus) }
            _state.update { it.copy(isEditBonusOpen = false) }
        }
    }

    private fun buildHistory(
        from: LocalDate,
        goals: List<DailyGoal>,
        summaries: List<DailySummary>,
        fallbackTargetMl: Int
    ): List<GoalHistoryDay> {
        val goalByDate = goals.associateBy { it.date }
        val summaryByDate = summaries.associateBy { it.date }
        val firstRecorded = (goals.map { it.date } + summaries.map { it.date }).minOrNull()
            ?: return emptyList()
        val start = maxOf(from, firstRecorded)
        val days = mutableListOf<GoalHistoryDay>()
        var date = start
        while (date < today) {
            val goal = goalByDate[date]
            val consumedMl = summaryByDate[date]?.totalMl ?: 0
            val targetMl = goal?.targetMl ?: fallbackTargetMl
            val isCompleted = goal?.isCompleted ?: (targetMl > 0 && consumedMl >= targetMl)
            days += GoalHistoryDay(date, consumedMl, targetMl, isCompleted)
            date = date.plus(DatePeriod(days = 1))
        }
        return days.asReversed()
    }

    private companion object {
        const val HISTORY_DAYS = 30
    }
}
