package com.example.waterbalancemonitor.presentation.screens.goals.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalsState

@Composable
fun GoalProgressCard(
    state: GoalsState,
    onEditBonus: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    text = state.targetMl.toString(),
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
                progress = { state.progress },
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
                    value = state.consumedMl
                )
                LabeledValue(
                    label = stringResource(R.string.goals_remaining),
                    value = state.remainingMl,
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
                    value = state.targetMl,
                    alignEnd = true
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(isCompleted = state.isCompleted)
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onEditBonus) {
                    Text(stringResource(R.string.goals_edit_bonus))
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(isCompleted: Boolean) {
    val containerColor = if (isCompleted) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    val contentColor = if (isCompleted) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }
    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = if (isCompleted) Icons.Filled.Check else Icons.Filled.Info,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(
                    if (isCompleted) {
                        R.string.goals_completed
                    } else {
                        R.string.goals_in_progress
                    }
                ),
                style = MaterialTheme.typography.labelLarge
            )
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
