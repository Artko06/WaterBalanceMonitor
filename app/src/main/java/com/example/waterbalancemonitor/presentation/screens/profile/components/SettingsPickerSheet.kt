package com.example.waterbalancemonitor.presentation.screens.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.AppLanguage
import com.example.waterbalancemonitor.domain.model.ThemeMode
import com.example.waterbalancemonitor.presentation.mapper.labelRes
import com.example.waterbalancemonitor.presentation.screens.profile.state.SettingsField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPickerSheet(
    field: SettingsField,
    themeMode: ThemeMode,
    language: AppLanguage,
    onThemeSelected: (ThemeMode) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = when (field) {
                    SettingsField.THEME -> stringResource(R.string.settings_theme)
                    SettingsField.LANGUAGE -> stringResource(R.string.settings_language)
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            when (field) {
                SettingsField.THEME -> ThemeMode.entries.forEach { option ->
                    OptionRow(
                        label = stringResource(option.labelRes()),
                        selected = option == themeMode,
                        onClick = { onThemeSelected(option) }
                    )
                }

                SettingsField.LANGUAGE -> AppLanguage.entries.forEach { option ->
                    OptionRow(
                        label = stringResource(option.labelRes()),
                        selected = option == language,
                        onClick = { onLanguageSelected(option) }
                    )
                }
            }
        }
    }
}

@Composable
private fun OptionRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
