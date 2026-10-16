package com.example.waterbalancemonitor.presentation.screens.achievements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementIcon
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import com.example.waterbalancemonitor.presentation.components.rememberEnterAnimation
import com.example.waterbalancemonitor.presentation.mapper.labelRes
import com.example.waterbalancemonitor.presentation.screens.achievements.components.AchievementCard
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementGroupState
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementItem
import com.example.waterbalancemonitor.presentation.screens.achievements.viewmodel.AchievementGroupViewModel
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme

@Composable
fun AchievementGroupScreen(
    category: AchievementCategory,
    modifier: Modifier = Modifier,
    viewModel: AchievementGroupViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AchievementGroupContent(
        category = category,
        state = state,
        modifier = modifier
    )
}

@Composable
private fun AchievementGroupContent(
    category: AchievementCategory,
    state: AchievementGroupState,
    modifier: Modifier = Modifier
) {
    val entered = rememberEnterAnimation()
    val items = state.items.filter { it.category == category }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = stringResource(category.labelRes()),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        items(items, key = { it.id }) { item ->
            AchievementCard(
                item = item,
                entered = entered,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AchievementGroupContentPreview() {
    WaterBalanceMonitorTheme {
        AchievementGroupContent(
            category = AchievementCategory.STREAK,
            state = AchievementGroupState(
                items = listOf(
                    AchievementItem(
                        id = 1,
                        title = "3-day streak",
                        description = "Reach the norm 3 days in a row",
                        icon = AchievementIcon.STREAK,
                        category = AchievementCategory.STREAK,
                        progress = 3,
                        target = 3,
                        status = AchievementStatus.COMPLETED
                    ),
                    AchievementItem(
                        id = 2,
                        title = "Week streak",
                        description = "Reach the norm 7 days in a row",
                        icon = AchievementIcon.STREAK,
                        category = AchievementCategory.STREAK,
                        progress = 4,
                        target = 7,
                        status = AchievementStatus.IN_PROGRESS
                    ),
                    AchievementItem(
                        id = 3,
                        title = "Two-week streak",
                        description = "Reach the norm 14 days in a row",
                        icon = AchievementIcon.STREAK,
                        category = AchievementCategory.STREAK,
                        progress = 0,
                        target = 14,
                        status = AchievementStatus.LOCKED
                    )
                ),
                isLoading = false
            )
        )
    }
}
