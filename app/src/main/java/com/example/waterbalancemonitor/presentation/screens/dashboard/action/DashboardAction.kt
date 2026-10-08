package com.example.waterbalancemonitor.presentation.screens.dashboard.action

import com.example.waterbalancemonitor.domain.model.IntakeEvent

sealed interface DashboardAction {

    data object ToggleAddPanel : DashboardAction

    data class DrinkTypeSelected(val id: Long) : DashboardAction

    data object OpenDrinkPicker : DashboardAction

    data object DismissDrinkPicker : DashboardAction

    data object OpenVesselPicker : DashboardAction

    data object DismissVesselPicker : DashboardAction

    data class VesselSelected(val id: Long?) : DashboardAction

    data class VolumeChanged(val volumeMl: Int) : DashboardAction

    data object AddClicked : DashboardAction

    data class DeleteIntake(val event: IntakeEvent) : DashboardAction

    data object ChangeGoalClicked : DashboardAction
}
