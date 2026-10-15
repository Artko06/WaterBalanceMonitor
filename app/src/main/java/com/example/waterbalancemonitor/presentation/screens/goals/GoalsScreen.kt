package com.example.waterbalancemonitor.presentation.screens.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.waterbalancemonitor.presentation.screens.goals.action.GoalsAction
import com.example.waterbalancemonitor.presentation.screens.goals.components.EditBonusSheet
import com.example.waterbalancemonitor.presentation.screens.goals.components.GoalHistoryItem
import com.example.waterbalancemonitor.presentation.screens.goals.components.GoalProgressCard
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalHistoryDay
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalsState
import com.example.waterbalancemonitor.presentation.screens.goals.viewmodel.GoalsViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme
import kotlinx.datetime.LocalDate

@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier,
    viewModel: GoalsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GoalsContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (state.isEditBonusOpen) {
        EditBonusSheet(state = state, onAction = viewModel::onAction)
    }
}

@Composable
private fun GoalsContent(
    state: GoalsState,
    onAction: (GoalsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val entered = rememberEnterAnimation()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            GoalProgressCard(
                state = state,
                onEditBonus = { onAction(GoalsAction.OpenBonusEditor) },
                entered = entered
            )
        }
        item {
            Text(
                text = stringResource(R.string.goals_history),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        if (state.history.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.goals_history_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(state.history, key = { it.date }) { day ->
                GoalHistoryItem(day = day)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalsContentPreview() {
    WaterBalanceMonitorTheme {
        GoalsContent(
            state = GoalsState(
                name = "Alex",
                baseNormMl = 2500,
                bonusMl = 300,
                targetMl = 2800,
                consumedMl = 1700,
                history = listOf(
                    GoalHistoryDay(LocalDate(2026, 10, 14), 2600, 2500, true),
                    GoalHistoryDay(LocalDate(2026, 10, 13), 1200, 2500, false),
                    GoalHistoryDay(LocalDate(2026, 10, 12), 0, 2500, false)
                ),
                isLoading = false
            ),
            onAction = {}
        )
    }
}
