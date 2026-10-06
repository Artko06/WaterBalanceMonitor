package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.ThemeMode
import com.example.waterbalancemonitor.domain.model.UnitSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DayOfWeek

interface SettingsRepository {

    val themeMode: Flow<ThemeMode>

    val unitSystem: Flow<UnitSystem>

    val firstDayOfWeek: Flow<DayOfWeek>

    val language: Flow<String>

    suspend fun setThemeMode(mode: ThemeMode)

    suspend fun setUnitSystem(unitSystem: UnitSystem)

    suspend fun setFirstDayOfWeek(dayOfWeek: DayOfWeek)

    suspend fun setLanguage(language: String)
}
