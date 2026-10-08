package com.example.waterbalancemonitor.presentation.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.presentation.mapper.toDrawableRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkPickerSheet(
    drinkTypes: List<DrinkType>,
    selectedId: Long?,
    onSelect: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.dashboard_drink_type),
                style = MaterialTheme.typography.titleLarge
            )
            drinkTypes.orderedForPicker().chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { drinkType ->
                        DrinkCell(
                            drinkType = drinkType,
                            selected = drinkType.id == selectedId,
                            onClick = { onSelect(drinkType.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

private val PREFERRED_DRINK_ORDER = listOf("Water", "Tea", "Coffee")

private fun List<DrinkType>.orderedForPicker(): List<DrinkType> = sortedWith(
    compareBy(
        { drinkType ->
            val index = PREFERRED_DRINK_ORDER.indexOf(drinkType.name)
            if (index == -1) PREFERRED_DRINK_ORDER.size else index
        },
        { it.name }
    )
)

@Composable
private fun DrinkCell(
    drinkType: DrinkType,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(drinkType.icon.toDrawableRes()),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = drinkType.name,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor,
            textAlign = TextAlign.Center
        )
    }
}
