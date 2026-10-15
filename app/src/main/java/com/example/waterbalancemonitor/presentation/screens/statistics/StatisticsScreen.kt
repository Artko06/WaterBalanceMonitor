package com.example.waterbalancemonitor.presentation.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.rememberEnterAnimation
import com.example.waterbalancemonitor.presentation.screens.statistics.action.StatisticsAction
import com.example.waterbalancemonitor.presentation.screens.statistics.components.StatsExtremesCard
import com.example.waterbalancemonitor.presentation.screens.statistics.components.StatsPeriodSelector
import com.example.waterbalancemonitor.presentation.screens.statistics.components.StatsStreakCard
import com.example.waterbalancemonitor.presentation.screens.statistics.components.StatsSummaryCard
import com.example.waterbalancemonitor.presentation.screens.statistics.state.StatisticsState
import com.example.waterbalancemonitor.presentation.screens.statistics.viewmodel.StatisticsViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme
import kotlinx.datetime.LocalDate

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StatisticsContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
private fun StatisticsContent(
    state: StatisticsState,
    onAction: (StatisticsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val entered = rememberEnterAnimation()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.stats_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            StatsPeriodSelector(
                selected = state.period,
                onSelect = { onAction(StatisticsAction.PeriodSelected(it)) }
            )
        }
        if (!state.isLoading && !state.hasData) {
            item {
                Text(
                    text = stringResource(R.string.stats_no_data),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            item { StatsSummaryCard(state = state, entered = entered) }
            item { StatsStreakCard(state = state, entered = entered) }
            item { StatsExtremesCard(state = state, entered = entered) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatisticsContentPreview() {
    WaterBalanceMonitorTheme {
        StatisticsContent(
            state = StatisticsState(
                totalMl = 14_200,
                averageMl = 2_030,
                daysCount = 7,
                completedDays = 5,
                completedPercent = 71,
                bestDayMl = 2_800,
                bestDate = LocalDate(2026, 10, 12),
                worstDayMl = 900,
                worstDate = LocalDate(2026, 10, 9),
                currentStreak = 3,
                longestStreak = 6,
                trendPercent = 8,
                isLoading = false
            ),
            onAction = {}
        )
    }
}
