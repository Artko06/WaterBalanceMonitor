package com.example.waterbalancemonitor.presentation.mapper

import androidx.annotation.StringRes
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.AppLanguage
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.domain.model.ThemeMode

@StringRes
fun Gender.labelRes(): Int = when (this) {
    Gender.MALE -> R.string.gender_male
    Gender.FEMALE -> R.string.gender_female
}

@StringRes
fun ActivityLevel.labelRes(): Int = when (this) {
    ActivityLevel.SEDENTARY -> R.string.activity_sedentary
    ActivityLevel.LIGHT -> R.string.activity_light
    ActivityLevel.MODERATE -> R.string.activity_moderate
    ActivityLevel.ACTIVE -> R.string.activity_active
    ActivityLevel.VERY_ACTIVE -> R.string.activity_very_active
}

@StringRes
fun ActivityLevel.descriptionRes(): Int = when (this) {
    ActivityLevel.SEDENTARY -> R.string.activity_sedentary_desc
    ActivityLevel.LIGHT -> R.string.activity_light_desc
    ActivityLevel.MODERATE -> R.string.activity_moderate_desc
    ActivityLevel.ACTIVE -> R.string.activity_active_desc
    ActivityLevel.VERY_ACTIVE -> R.string.activity_very_active_desc
}

@StringRes
fun AchievementCategory.labelRes(): Int = when (this) {
    AchievementCategory.START -> R.string.achievements_category_start
    AchievementCategory.DAILY -> R.string.achievements_category_daily
    AchievementCategory.STREAK -> R.string.achievements_category_streak
    AchievementCategory.TOTAL -> R.string.achievements_category_total
    AchievementCategory.TIME -> R.string.achievements_category_time
    AchievementCategory.OTHER -> R.string.achievements_category_other
}

@StringRes
fun ThemeMode.labelRes(): Int = when (this) {
    ThemeMode.LIGHT -> R.string.theme_light
    ThemeMode.DARK -> R.string.theme_dark
    ThemeMode.SYSTEM -> R.string.theme_system
}

@StringRes
fun AppLanguage.labelRes(): Int = when (this) {
    AppLanguage.SYSTEM -> R.string.language_system
    AppLanguage.ENGLISH -> R.string.language_english
    AppLanguage.RUSSIAN -> R.string.language_russian
    AppLanguage.BELARUSIAN -> R.string.language_belarusian
}
