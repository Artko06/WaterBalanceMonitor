package com.example.waterbalancemonitor.presentation.screens.goals.action

sealed interface GoalsAction {

    data object OpenBonusEditor : GoalsAction

    data object DismissBonusEditor : GoalsAction

    data class BonusChanged(val ml: Int) : GoalsAction

    data object SaveBonusClicked : GoalsAction
}
