package com.example.waterbalancemonitor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

enum class StatusBadgeType {
    COMPLETED,
    IN_PROGRESS,
    LOCKED
}

@Composable
fun StatusBadge(
    type: StatusBadgeType,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier
) {
    val containerColor = when (type) {
        StatusBadgeType.COMPLETED -> MaterialTheme.colorScheme.primaryContainer
        StatusBadgeType.IN_PROGRESS -> MaterialTheme.colorScheme.secondaryContainer
        StatusBadgeType.LOCKED -> MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = when (type) {
        StatusBadgeType.COMPLETED -> MaterialTheme.colorScheme.onPrimaryContainer
        StatusBadgeType.IN_PROGRESS -> MaterialTheme.colorScheme.onSecondaryContainer
        StatusBadgeType.LOCKED -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val icon: ImageVector = when (type) {
        StatusBadgeType.COMPLETED -> Icons.Filled.Check
        StatusBadgeType.IN_PROGRESS -> Icons.Filled.Info
        StatusBadgeType.LOCKED -> Icons.Filled.Lock
    }
    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(labelRes),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
