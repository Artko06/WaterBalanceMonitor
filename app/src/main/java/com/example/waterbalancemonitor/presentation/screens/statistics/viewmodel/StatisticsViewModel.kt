package com.example.waterbalancemonitor.presentation.screens.statistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.Statistics
import com.example.waterbalancemonitor.domain.model.Streak
import com.example.waterbalancemonitor.domain.usecase.statistics.GetStatisticsUseCase
import com.example.waterbalancemonitor.domain.usecase.statistics.GetStreakUseCase
import com.example.waterbalancemonitor.presentation.screens.statistics.action.StatisticsAction
import com.example.waterbalancemonitor.presentation.screens.statistics.state.StatsPeriod
import com.example.waterbalancemonitor.presentation.screens.statistics.state.StatisticsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatistics: GetStatisticsUseCase,
    private val getStreak: GetStreakUseCase
) : ViewModel() {

    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    private val period = MutableStateFlow(StatsPeriod.WEEK)

    private val _state = MutableStateFlow(StatisticsState())
    val state: StateFlow<StatisticsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            period
                .flatMapLatest { selected ->
                    val (from, to) = selected.currentRange(today)
                    val (previousFrom, previousTo) = selected.previousRange(today)
                    combine(
                        getStatistics(from, to),
                        getStatistics(previousFrom, previousTo),
                        getStreak()
                    ) { stats, previous, streak ->
                        StatsBundle(selected, stats, previous, streak)
                    }
                }
                .collect { bundle ->
                    _state.update {
                        it.copy(
                            period = bundle.period,
                            totalMl = bundle.stats.totalMl,
                            averageMl = bundle.stats.averageMl,
                            daysCount = bundle.stats.daysCount,
                            completedDays = bundle.stats.completedDays,
                            completedPercent = bundle.stats.completedPercent,
                            bestDayMl = bundle.stats.bestDayMl,
                            bestDate = bundle.stats.bestDate,
                            worstDayMl = bundle.stats.worstDayMl,
                            worstDate = bundle.stats.worstDate,
                            currentStreak = bundle.streak.current,
                            longestStreak = bundle.streak.longest,
                            trendPercent = trendOf(
                                current = bundle.stats.averageMl,
                                previous = bundle.previous.averageMl
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: StatisticsAction) {
        when (action) {
            is StatisticsAction.PeriodSelected -> period.value = action.period
        }
    }

    private fun trendOf(current: Int, previous: Int): Int? {
        if (previous <= 0) return null
        return (current - previous) * 100 / previous
    }

    private data class StatsBundle(
        val period: StatsPeriod,
        val stats: Statistics,
        val previous: Statistics,
        val streak: Streak
    )
}
