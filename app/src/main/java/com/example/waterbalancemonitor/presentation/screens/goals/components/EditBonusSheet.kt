package com.example.waterbalancemonitor.presentation.screens.goals.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.components.NumberEditor
import com.example.waterbalancemonitor.presentation.screens.goals.action.GoalsAction
import com.example.waterbalancemonitor.presentation.screens.goals.state.GoalsState
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBonusSheet(
    state: GoalsState,
    onAction: (GoalsAction) -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onAction(GoalsAction.DismissBonusEditor) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.goals_bonus_editor_title),
                style = MaterialTheme.typography.titleLarge
            )
            NumberEditor(
                value = state.bonusDraftMl.toFloat(),
                range = GoalsState.MIN_BONUS_ML.toFloat()..GoalsState.MAX_BONUS_ML.toFloat(),
                step = GoalsState.BONUS_STEP.toFloat(),
                unit = stringResource(R.string.unit_ml),
                onValueChange = { onAction(GoalsAction.BonusChanged(it.roundToInt())) },
                onApply = { onAction(GoalsAction.SaveBonusClicked) }
            )
            Text(
                text = stringResource(
                    R.string.goals_bonus_preview,
                    state.baseNormMl + state.bonusDraftMl,
                    stringResource(R.string.unit_ml)
                ),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
