package com.example.waterbalancemonitor.presentation.screens.statistics.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

@Composable
fun StatsExtremesCard(
    state: StatisticsState,
    entered: Boolean,
    modifier: Modifier = Modifier
) {
    if (state.bestDate == null && state.worstDate == null) return

    val animatedBest by animateIntAsState(
        targetValue = if (entered) state.bestDayMl else 0,
        label = "statsBest"
    )
    val animatedWorst by animateIntAsState(
        targetValue = if (entered) state.worstDayMl else 0,
        label = "statsWorst"
    )

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExtremeRow(
                label = stringResource(R.string.stats_best_day),
                valueMl = animatedBest,
                date = state.bestDate
            )
            if (state.worstDate != null) {
                HorizontalDivider()
                ExtremeRow(
                    label = stringResource(R.string.stats_worst_day),
                    valueMl = animatedWorst,
                    date = state.worstDate
                )
            }
        }
    }
}

@Composable
private fun ExtremeRow(
    label: String,
    valueMl: Int,
    date: LocalDate?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (date != null) {
                Text(
                    text = date.format(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = "$valueMl ${stringResource(R.string.unit_ml)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun LocalDate.format(): String =
    "%02d.%02d.%04d".format(day, month.number, year)
