package com.example.waterbalancemonitor.presentation.screens.profile.components

import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileField

fun Gender.labelRes(): Int = when (this) {
    Gender.MALE -> R.string.gender_male
    Gender.FEMALE -> R.string.gender_female
}

fun ActivityLevel.labelRes(): Int = when (this) {
    ActivityLevel.SEDENTARY -> R.string.activity_sedentary
    ActivityLevel.LIGHT -> R.string.activity_light
    ActivityLevel.MODERATE -> R.string.activity_moderate
    ActivityLevel.ACTIVE -> R.string.activity_active
    ActivityLevel.VERY_ACTIVE -> R.string.activity_very_active
}

fun ActivityLevel.descriptionRes(): Int = when (this) {
    ActivityLevel.SEDENTARY -> R.string.activity_sedentary_desc
    ActivityLevel.LIGHT -> R.string.activity_light_desc
    ActivityLevel.MODERATE -> R.string.activity_moderate_desc
    ActivityLevel.ACTIVE -> R.string.activity_active_desc
    ActivityLevel.VERY_ACTIVE -> R.string.activity_very_active_desc
}

fun ProfileField.titleRes(): Int = when (this) {
    ProfileField.NAME -> R.string.profile_name
    ProfileField.WEIGHT -> R.string.profile_weight
    ProfileField.HEIGHT -> R.string.profile_height
    ProfileField.AGE -> R.string.profile_age
    ProfileField.GENDER -> R.string.profile_gender
    ProfileField.ACTIVITY -> R.string.profile_activity
    ProfileField.GOAL -> R.string.profile_goal_override
}

fun ProfileField.errorRes(): Int = when (this) {
    ProfileField.NAME -> R.string.error_name_required
    ProfileField.WEIGHT -> R.string.error_weight_invalid
    ProfileField.HEIGHT -> R.string.error_height_invalid
    ProfileField.AGE -> R.string.error_age_invalid
    ProfileField.GOAL -> R.string.error_goal_invalid
    ProfileField.GENDER, ProfileField.ACTIVITY -> R.string.error_name_required
}
