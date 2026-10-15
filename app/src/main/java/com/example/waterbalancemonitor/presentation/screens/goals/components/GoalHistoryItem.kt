package com.example.waterbalancemonitor.presentation.screens.goals.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalHistoryDay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

@Composable
fun GoalHistoryItem(
    day: GoalHistoryDay,
    modifier: Modifier = Modifier
) {
    val colors = if (day.isCompleted) {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    } else {
        CardDefaults.cardColors()
    }
    val contentColor = colors.contentColor
    val accentColor = if (day.isCompleted) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = colors
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = day.date.format(),
                        style = MaterialTheme.typography.titleSmall,
                        color = contentColor
                    )
                    Text(
                        text = stringResource(
                            if (day.isCompleted) {
                                R.string.goals_status_completed
                            } else {
                                R.string.goals_status_missed
                            }
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        color = accentColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = stringResource(
                        R.string.goals_history_progress,
                        day.consumedMl,
                        day.targetMl,
                        stringResource(R.string.unit_ml)
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor
                )
                LinearProgressIndicator(
                    progress = { day.progress },
                    color = accentColor,
                    trackColor = contentColor.copy(alpha = 0.2f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.goals_percent, day.percent),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

private fun LocalDate.format(): String =
    "%02d.%02d.%04d".format(day, month.number, year)
