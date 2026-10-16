package com.example.waterbalancemonitor.presentation.screens.goals.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.waterbalancemonitor.presentation.components.StatusBadgeType
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalsState

@Composable
fun GoalProgressCard(
    state: GoalsState,
    onEditBonus: () -> Unit,
    modifier: Modifier = Modifier,
    entered: Boolean = true
) {
    val animatedTarget by animateIntAsState(
        targetValue = if (entered) state.targetMl else 0,
        label = "goalTarget"
    )
    val animatedConsumed by animateIntAsState(
        targetValue = if (entered) state.consumedMl else 0,
        label = "goalConsumed"
    )
    val animatedRemaining by animateIntAsState(
        targetValue = if (entered) state.remainingMl else 0,
        label = "goalRemaining"
    )
    val animatedProgress by animateFloatAsState(
        targetValue = if (entered) state.progress else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "goalProgress"
    )
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.goals_daily_goal),
                style = MaterialTheme.typography.titleMedium
            )
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = animatedTarget.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.unit_ml),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabeledValue(
                    label = stringResource(R.string.goals_consumed),
                    value = animatedConsumed
                )
                LabeledValue(
                    label = stringResource(R.string.goals_remaining),
                    value = animatedRemaining,
                    alignEnd = true
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabeledValue(
                    label = stringResource(R.string.goals_base_norm),
                    value = state.baseNormMl
                )
                LabeledValue(
                    label = stringResource(R.string.goals_bonus),
                    value = state.bonusMl
                )
                LabeledValue(
                    label = stringResource(R.string.goals_daily_goal),
                    value = animatedTarget,
                    alignEnd = true
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(
                    type = if (state.isCompleted) {
                        StatusBadgeType.COMPLETED
                    } else {
                        StatusBadgeType.IN_PROGRESS
                    },
                    labelRes = if (state.isCompleted) {
                        R.string.goals_completed
                    } else {
                        R.string.goals_in_progress
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onEditBonus) {
                    Text(stringResource(R.string.goals_edit_bonus))
                }
            }
        }
    }
}

@Composable
private fun LabeledValue(
    label: String,
    value: Int,
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
            text = "$value ${stringResource(R.string.unit_ml)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
