package com.example.waterbalancemonitor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.presentation.mapper.labelRes

@Composable
fun GenderSelector(
    selected: Gender,
    onSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Gender.entries.forEach { gender ->
            FilterChip(
                selected = selected == gender,
                onClick = { onSelected(gender) },
                label = { Text(stringResource(gender.labelRes())) }
            )
        }
    }
}
