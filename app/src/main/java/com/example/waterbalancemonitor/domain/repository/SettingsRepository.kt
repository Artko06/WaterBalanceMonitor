package com.example.waterbalancemonitor.domain.repository

import com.example.waterbalancemonitor.domain.model.AppLanguage
import com.example.waterbalancemonitor.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val themeMode: Flow<ThemeMode>

    val language: Flow<AppLanguage>

    suspend fun setThemeMode(mode: ThemeMode)

    suspend fun setLanguage(language: AppLanguage)
}
