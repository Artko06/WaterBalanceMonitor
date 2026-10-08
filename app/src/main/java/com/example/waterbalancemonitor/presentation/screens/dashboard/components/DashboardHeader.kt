package com.example.waterbalancemonitor.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R

@Composable
fun DashboardHeader(
    name: String,
    remainingMl: Int,
    goalMl: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = if (name.isBlank()) {
                stringResource(R.string.dashboard_greeting_no_name)
            } else {
                stringResource(R.string.dashboard_greeting, name)
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (goalMl > 0) {
            Text(
                text = stringResource(
                    R.string.dashboard_left,
                    remainingMl,
                    stringResource(R.string.unit_ml)
                ),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = stringResource(R.string.dashboard_set_goal_hint),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
