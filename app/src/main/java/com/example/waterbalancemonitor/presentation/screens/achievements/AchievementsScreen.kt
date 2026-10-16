package com.example.waterbalancemonitor.presentation.screens.achievements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementIcon
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import com.example.waterbalancemonitor.presentation.components.rememberEnterAnimation
import com.example.waterbalancemonitor.presentation.screens.achievements.components.AchievementGroupCard
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementGroup
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementItem
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementsState
import com.example.waterbalancemonitor.presentation.screens.achievements.viewmodel.AchievementsViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme

@Composable
fun AchievementsScreen(
    onNavigateToGroup: (AchievementCategory) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AchievementsContent(
        state = state,
        onGroupClick = onNavigateToGroup,
        modifier = modifier
    )
}

@Composable
private fun AchievementsContent(
    state: AchievementsState,
    onGroupClick: (AchievementCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val entered = rememberEnterAnimation()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Text(
                    text = stringResource(R.string.achievements_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(
                        R.string.achievements_completed_count,
                        state.completedCount,
                        state.totalCount
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        items(state.groups, key = { it.category.name }) { group ->
            AchievementGroupCard(
                group = group,
                entered = entered,
                onClick = { onGroupClick(group.category) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AchievementsContentPreview() {
    WaterBalanceMonitorTheme {
        AchievementsContent(
            state = AchievementsState(
                groups = listOf(
                    AchievementGroup(
                        category = AchievementCategory.START,
                        items = listOf(
                            AchievementItem(
                                id = 1,
                                title = "First glass",
                                description = "Log your first intake",
                                icon = AchievementIcon.FIRST,
                                category = AchievementCategory.START,
                                progress = 1,
                                target = 1,
                                status = AchievementStatus.COMPLETED
                            )
                        )
                    ),
                    AchievementGroup(
                        category = AchievementCategory.STREAK,
                        items = listOf(
                            AchievementItem(
                                id = 2,
                                title = "3-day streak",
                                description = "Reach the norm 3 days in a row",
                                icon = AchievementIcon.STREAK,
                                category = AchievementCategory.STREAK,
                                progress = 2,
                                target = 3,
                                status = AchievementStatus.IN_PROGRESS
                            ),
                            AchievementItem(
                                id = 3,
                                title = "Week streak",
                                description = "Reach the norm 7 days in a row",
                                icon = AchievementIcon.STREAK,
                                category = AchievementCategory.STREAK,
                                progress = 0,
                                target = 7,
                                status = AchievementStatus.LOCKED
                            )
                        )
                    )
                ),
                completedCount = 1,
                totalCount = 3,
                isLoading = false
            ),
            onGroupClick = {}
        )
    }
}
