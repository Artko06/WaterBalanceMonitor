package com.example.waterbalancemonitor.presentation.screens.history.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.StatusBadge
import com.example.waterbalancemonitor.presentation.screens.history.state.HistoryState

@Composable
fun DaySummaryCard(
    state: HistoryState,
    entered: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedTotalMl by animateIntAsState(
        targetValue = if (entered) state.dayTotalMl else 0,
        label = "historyTotal"
    )
    val animatedTargetMl by animateIntAsState(
        targetValue = if (entered) state.goalTargetMl else 0,
        label = "historyTarget"
    )
    val animatedPercent by animateIntAsState(
        targetValue = if (entered) state.percent else 0,
        label = "historyPercent"
    )
    val animatedProgress by animateFloatAsState(
        targetValue = if (entered) state.progress else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "historyProgress"
    )

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ValueBlock(
                    label = stringResource(R.string.history_day_drank),
                    valueMl = animatedTotalMl
                )
                if (state.goalTargetMl > 0) {
                    ValueBlock(
                        label = stringResource(R.string.history_day_goal),
                        valueMl = animatedTargetMl,
                        alignEnd = true
                    )
                }
            }
            if (state.goalTargetMl > 0) {
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.goals_percent, animatedPercent),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    StatusBadge(
                        isCompleted = state.isCompleted,
                        completedLabelRes = R.string.history_completed,
                        incompleteLabelRes = R.string.history_not_completed
                    )
                }
            }
        }
    }
}

@Composable
private fun ValueBlock(
    label: String,
    valueMl: Int,
    alignEnd: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "$valueMl ${stringResource(R.string.unit_ml)}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
