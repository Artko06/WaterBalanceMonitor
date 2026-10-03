package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsPreferencesRepository {
    val themeMode: Flow<ThemeMode>

    suspend fun setThemeMode(mode: ThemeMode)
}
