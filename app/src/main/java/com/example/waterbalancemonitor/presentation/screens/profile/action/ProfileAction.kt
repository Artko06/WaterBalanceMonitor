package com.example.waterbalancemonitor.presentation.screens.profile.action

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.presentation.screens.profile.state.ProfileField

sealed interface ProfileAction {

    data class FieldClicked(val field: ProfileField) : ProfileAction

    data object DismissSheet : ProfileAction

    data class DraftTextChanged(val value: String) : ProfileAction

    data class DraftNumberChanged(val value: Float) : ProfileAction

    data class GenderSelected(val gender: Gender) : ProfileAction

    data class ActivitySelected(val activityLevel: ActivityLevel) : ProfileAction

    data class ManualGoalToggled(val enabled: Boolean) : ProfileAction

    data object ApplyClicked : ProfileAction

    data object NormInfoClicked : ProfileAction
}
