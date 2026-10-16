package com.example.waterbalancemonitor.presentation.screens.achievements.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import com.example.waterbalancemonitor.presentation.components.StatusBadge
import com.example.waterbalancemonitor.presentation.components.StatusBadgeType
import com.example.waterbalancemonitor.presentation.mapper.toDrawableRes
import com.example.waterbalancemonitor.presentation.screens.achievements.state.AchievementItem

@Composable
fun AchievementCard(
    item: AchievementItem,
    entered: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (entered) item.progressFraction else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "achievementProgress"
    )
    val containerColor = if (item.status == AchievementStatus.LOCKED) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        CardDefaults.cardColors().containerColor
    }
    val iconBackground = when (item.status) {
        AchievementStatus.COMPLETED -> MaterialTheme.colorScheme.primaryContainer
        AchievementStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondaryContainer
        AchievementStatus.LOCKED -> MaterialTheme.colorScheme.surface
    }
    val iconTint = when (item.status) {
        AchievementStatus.COMPLETED -> MaterialTheme.colorScheme.primary
        AchievementStatus.IN_PROGRESS -> MaterialTheme.colorScheme.onSecondaryContainer
        AchievementStatus.LOCKED -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(item.icon.toDrawableRes()),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            StatusBadge(
                type = item.status.toBadgeType(),
                labelRes = item.status.labelRes()
            )
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )
            Text(
                text = stringResource(R.string.stats_percent, item.progressPercent),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun AchievementStatus.toBadgeType(): StatusBadgeType = when (this) {
    AchievementStatus.COMPLETED -> StatusBadgeType.COMPLETED
    AchievementStatus.IN_PROGRESS -> StatusBadgeType.IN_PROGRESS
    AchievementStatus.LOCKED -> StatusBadgeType.LOCKED
}

@StringRes
private fun AchievementStatus.labelRes(): Int = when (this) {
    AchievementStatus.COMPLETED -> R.string.achievements_completed
    AchievementStatus.IN_PROGRESS -> R.string.achievements_in_progress
    AchievementStatus.LOCKED -> R.string.achievements_locked
}
