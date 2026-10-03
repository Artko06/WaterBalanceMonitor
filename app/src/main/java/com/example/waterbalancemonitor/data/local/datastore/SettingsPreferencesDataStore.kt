package com.example.waterbalancemonitor.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.waterbalancemonitor.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SettingsPreferencesDataStore {

    fun observeThemeMode(dataStore: DataStore<Preferences>): Flow<ThemeMode> =
        dataStore.data.map { preferences ->
            preferences[SettingsPreferencesDataStoreKeys.THEME_MODE]
                ?.let { stored -> ThemeMode.entries.firstOrNull { it.name == stored } }
                ?: ThemeMode.SYSTEM
        }

    suspend fun setThemeMode(dataStore: DataStore<Preferences>, mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[SettingsPreferencesDataStoreKeys.THEME_MODE] = mode.name
        }
    }
}
