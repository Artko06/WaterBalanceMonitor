package com.example.waterbalancemonitor.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStore
import com.example.waterbalancemonitor.domain.model.ThemeMode
import com.example.waterbalancemonitor.domain.repository.SettingsPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsPreferencesRepositoryImpl @Inject constructor(
    private val settingsPrefDataStore: DataStore<Preferences>
) : SettingsPreferencesRepository {

    override val themeMode: Flow<ThemeMode> =
        SettingsPreferencesDataStore.observeThemeMode(settingsPrefDataStore)

    override suspend fun setThemeMode(mode: ThemeMode) {
        SettingsPreferencesDataStore.setThemeMode(settingsPrefDataStore, mode)
    }
}
