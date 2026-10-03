package com.example.waterbalancemonitor.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsPreferencesDataStoreKeys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val UNIT_SYSTEM = stringPreferencesKey("unit_system")
    val LANGUAGE = stringPreferencesKey("language")
    val FIRST_DAY_OF_WEEK = stringPreferencesKey("first_day_of_week")
}
