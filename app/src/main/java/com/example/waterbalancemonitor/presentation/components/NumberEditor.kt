package com.example.waterbalancemonitor.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun NumberEditor(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float,
    unit: String,
    onValueChange: (Float) -> Unit,
    onApply: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var hadFocus by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    fun commitInput() {
        val parsed = input.toFloatOrNull()
        if (parsed != null) {
            onValueChange(parsed.coerceIn(range))
        }
        isEditing = false
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = input,
                onValueChange = { raw -> input = raw.filter { it.isDigit() || it == '.' } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { commitInput() }),
                suffix = { Text(unit) },
                textStyle = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { state ->
                        if (state.isFocused) {
                            hadFocus = true
                        } else if (hadFocus) {
                            commitInput()
                        }
                    }
            )
        } else {
            Text(
                text = "${formatNumber(value)} $unit",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    input = formatNumber(value)
                    hadFocus = false
                    isEditing = true
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onValueChange(snap(value - step, range, step)) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_minus),
                    contentDescription = stringResource(R.string.action_decrease)
                )
            }
            Slider(
                value = value,
                onValueChange = { raw -> onValueChange(snap(raw, range, step)) },
                valueRange = range,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onValueChange(snap(value + step, range, step)) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.action_increase)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatNumber(range.start),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = formatNumber(range.endInclusive),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (onApply != null) {
            Button(
                onClick = onApply,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.sheet_save))
            }
        }
    }

    LaunchedEffect(isEditing) {
        if (isEditing) focusRequester.requestFocus()
    }
}

private fun snap(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float
): Float {
    val snapped = ((value - range.start) / step).roundToInt() * step + range.start
    return snapped.coerceIn(range)
}

private fun formatNumber(value: Float): String =
    if (value % 1f == 0f) {
        value.roundToInt().toString()
    } else {
        String.format(Locale.US, "%.1f", value)
    }
