package com.example.waterbalancemonitor.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStore
import com.example.waterbalancemonitor.domain.model.AppLanguage
import com.example.waterbalancemonitor.domain.model.ThemeMode
import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override val themeMode: Flow<ThemeMode> =
        SettingsPreferencesDataStore.observeThemeMode(dataStore)

    override val language: Flow<AppLanguage> =
        SettingsPreferencesDataStore.observeLanguage(dataStore)

    override suspend fun setThemeMode(mode: ThemeMode) {
        SettingsPreferencesDataStore.setThemeMode(dataStore, mode)
    }

    override suspend fun setLanguage(language: AppLanguage) {
        SettingsPreferencesDataStore.setLanguage(dataStore, language)
    }
}
