package com.example.waterbalancemonitor.data.local.datastore

import com.example.waterbalancemonitor.domain.model.ThemeMode

object SettingsPreferencesDefaults {
    val THEME_MODE = ThemeMode.SYSTEM
    const val UNIT_SYSTEM = "METRIC"
    const val LANGUAGE = "ENGLISH"
    const val FIRST_DAY_OF_WEEK = "MONDAY"
}
