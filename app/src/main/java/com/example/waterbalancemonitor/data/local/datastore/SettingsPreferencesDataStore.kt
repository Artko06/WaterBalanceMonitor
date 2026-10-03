package com.example.waterbalancemonitor.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStoreKeys.FIRST_DAY_OF_WEEK
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStoreKeys.LANGUAGE
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStoreKeys.THEME_MODE
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStoreKeys.UNIT_SYSTEM
import com.example.waterbalancemonitor.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SettingsPreferencesDataStore {

    fun observeThemeMode(dataStore: DataStore<Preferences>): Flow<ThemeMode> =
        dataStore.data.map { preferences ->
            preferences[THEME_MODE]
                ?.let { stored -> ThemeMode.entries.firstOrNull { it.name == stored } }
                ?: SettingsPreferencesDefaults.THEME_MODE
        }

    suspend fun setThemeMode(dataStore: DataStore<Preferences>, mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode.name
        }
    }

    fun observeUnitSystem(dataStore: DataStore<Preferences>): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[UNIT_SYSTEM] ?: SettingsPreferencesDefaults.UNIT_SYSTEM
        }

    suspend fun setUnitSystem(dataStore: DataStore<Preferences>, unitSystem: String) {
        dataStore.edit { preferences ->
            preferences[UNIT_SYSTEM] = unitSystem
        }
    }

    fun observeLanguage(dataStore: DataStore<Preferences>): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[LANGUAGE] ?: SettingsPreferencesDefaults.LANGUAGE
        }

    suspend fun setLanguage(dataStore: DataStore<Preferences>, language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }

    fun observeFirstDayOfWeek(dataStore: DataStore<Preferences>): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[FIRST_DAY_OF_WEEK] ?: SettingsPreferencesDefaults.FIRST_DAY_OF_WEEK
        }

    suspend fun setFirstDayOfWeek(dataStore: DataStore<Preferences>, firstDayOfWeek: String) {
        dataStore.edit { preferences ->
            preferences[FIRST_DAY_OF_WEEK] = firstDayOfWeek
        }
    }
}
