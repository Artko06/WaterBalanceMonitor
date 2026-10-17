package com.example.waterbalancemonitor.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStoreKeys.LANGUAGE
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStoreKeys.THEME_MODE
import com.example.waterbalancemonitor.domain.model.AppLanguage
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

    fun observeLanguage(dataStore: DataStore<Preferences>): Flow<AppLanguage> =
        dataStore.data.map { preferences ->
            preferences[LANGUAGE]
                ?.let(AppLanguage::fromName)
                ?: SettingsPreferencesDefaults.LANGUAGE
        }

    suspend fun setLanguage(dataStore: DataStore<Preferences>, language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language.name
        }
    }
}
