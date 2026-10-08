package com.example.waterbalancemonitor.presentation.screens.norminfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.norminfo.components.DisclaimerCard
import com.example.waterbalancemonitor.presentation.screens.norminfo.components.HighlightCard
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme

@Composable
fun NormInfoScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.norm_info_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.norm_info_intro),
            style = MaterialTheme.typography.bodyLarge
        )
        HighlightCard(text = stringResource(R.string.norm_info_base))
        Text(
            text = stringResource(R.string.norm_info_personalization),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(R.string.norm_info_weight),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.norm_info_gender),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.norm_info_activity),
            style = MaterialTheme.typography.bodyMedium
        )
        HighlightCard(text = stringResource(R.string.norm_info_formula))
        DisclaimerCard(text = stringResource(R.string.norm_info_disclaimer))
        Text(
            text = stringResource(R.string.norm_info_sources),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NormInfoScreenPreview() {
    WaterBalanceMonitorTheme {
        NormInfoScreen()
    }
}
