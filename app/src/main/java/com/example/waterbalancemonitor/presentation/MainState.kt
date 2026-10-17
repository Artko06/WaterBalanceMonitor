package com.example.waterbalancemonitor.presentation

import com.example.waterbalancemonitor.domain.model.AppLanguage
import com.example.waterbalancemonitor.domain.model.ThemeMode

data class MainState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.SYSTEM,
    val startRoute: String? = null
)
