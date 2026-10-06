package com.example.waterbalancemonitor.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.waterbalancemonitor.data.local.datastore.SettingsPreferencesDataStore
import com.example.waterbalancemonitor.data.mapper.toEnumOrDefault
import com.example.waterbalancemonitor.domain.model.ThemeMode
import com.example.waterbalancemonitor.domain.model.UnitSystem
import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DayOfWeek
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override val themeMode: Flow<ThemeMode> =
        SettingsPreferencesDataStore.observeThemeMode(dataStore)

    override val unitSystem: Flow<UnitSystem> =
        SettingsPreferencesDataStore.observeUnitSystem(dataStore)
            .map { it.toEnumOrDefault(UnitSystem.METRIC) }

    override val firstDayOfWeek: Flow<DayOfWeek> =
        SettingsPreferencesDataStore.observeFirstDayOfWeek(dataStore)
            .map { it.toEnumOrDefault(DayOfWeek.MONDAY) }

    override val language: Flow<String> =
        SettingsPreferencesDataStore.observeLanguage(dataStore)

    override suspend fun setThemeMode(mode: ThemeMode) {
        SettingsPreferencesDataStore.setThemeMode(dataStore, mode)
    }

    override suspend fun setUnitSystem(unitSystem: UnitSystem) {
        SettingsPreferencesDataStore.setUnitSystem(dataStore, unitSystem.name)
    }

    override suspend fun setFirstDayOfWeek(dayOfWeek: DayOfWeek) {
        SettingsPreferencesDataStore.setFirstDayOfWeek(dataStore, dayOfWeek.name)
    }

    override suspend fun setLanguage(language: String) {
        SettingsPreferencesDataStore.setLanguage(dataStore, language)
    }
}
