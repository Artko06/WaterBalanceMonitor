package com.example.waterbalancemonitor.presentation.screens.statistics.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.statistics.state.StatisticsState

@Composable
fun StatsSummaryCard(
    state: StatisticsState,
    entered: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedTotal by animateIntAsState(
        targetValue = if (entered) state.totalMl else 0,
        label = "statsTotal"
    )
    val animatedAverage by animateIntAsState(
        targetValue = if (entered) state.averageMl else 0,
        label = "statsAverage"
    )
    val animatedPercent by animateIntAsState(
        targetValue = if (entered) state.completedPercent else 0,
        label = "statsPercent"
    )

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatValue(
                label = stringResource(R.string.stats_total),
                value = "$animatedTotal ${stringResource(R.string.unit_ml)}",
                large = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatValue(
                    label = stringResource(R.string.stats_average),
                    value = "$animatedAverage ${stringResource(R.string.unit_ml)}",
                    modifier = Modifier.weight(1f)
                )
                StatValue(
                    label = stringResource(R.string.stats_completed_percent),
                    value = stringResource(R.string.stats_percent, animatedPercent),
                    modifier = Modifier.weight(1f)
                )
            }
            TrendRow(trendPercent = state.trendPercent)
        }
    }
}

@Composable
private fun StatValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    large: Boolean = false
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = if (large) {
                MaterialTheme.typography.headlineSmall
            } else {
                MaterialTheme.typography.titleMedium
            },
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TrendRow(trendPercent: Int?) {
    if (trendPercent == null) return
    val label = if (trendPercent >= 0) {
        stringResource(R.string.stats_trend_up, trendPercent)
    } else {
        stringResource(R.string.stats_trend_down, -trendPercent)
    }
    val color = if (trendPercent >= 0) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = color
        )
    }
}
