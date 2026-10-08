package com.example.waterbalancemonitor.presentation.screens.profile.components

import androidx.annotation.StringRes
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileField

@StringRes
fun ProfileField.titleRes(): Int = when (this) {
    ProfileField.NAME -> R.string.profile_name
    ProfileField.WEIGHT -> R.string.profile_weight
    ProfileField.HEIGHT -> R.string.profile_height
    ProfileField.AGE -> R.string.profile_age
    ProfileField.GENDER -> R.string.profile_gender
    ProfileField.ACTIVITY -> R.string.profile_activity
    ProfileField.GOAL -> R.string.profile_goal_override
}

@StringRes
fun ProfileField.errorRes(): Int = when (this) {
    ProfileField.NAME -> R.string.error_name_required
    ProfileField.WEIGHT -> R.string.error_weight_invalid
    ProfileField.HEIGHT -> R.string.error_height_invalid
    ProfileField.AGE -> R.string.error_age_invalid
    ProfileField.GOAL -> R.string.error_goal_invalid
    ProfileField.GENDER, ProfileField.ACTIVITY -> R.string.error_name_required
}
