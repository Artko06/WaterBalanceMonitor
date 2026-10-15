package com.example.waterbalancemonitor.presentation.screens.history.action

import com.example.waterbalancemonitor.domain.model.IntakeEvent
import kotlinx.datetime.LocalDate

sealed interface HistoryAction {

    data object PreviousDay : HistoryAction

    data object NextDay : HistoryAction

    data object TodayClicked : HistoryAction

    data class DateSelected(val date: LocalDate) : HistoryAction

    data class OpenEditor(val event: IntakeEvent) : HistoryAction

    data object DismissEditor : HistoryAction

    data object OpenDrinkPicker : HistoryAction

    data object DismissDrinkPicker : HistoryAction

    data object OpenVesselPicker : HistoryAction

    data object DismissVesselPicker : HistoryAction

    data class EditDrinkTypeSelected(val id: Long) : HistoryAction

    data class EditVesselSelected(val id: Long?) : HistoryAction

    data class EditVolumeChanged(val ml: Int) : HistoryAction

    data class EditTimeChanged(val hour: Int, val minute: Int) : HistoryAction

    data object SaveEditClicked : HistoryAction

    data class DeleteIntake(val event: IntakeEvent) : HistoryAction
}
