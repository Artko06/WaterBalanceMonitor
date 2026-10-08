package com.example.waterbalancemonitor.presentation.screens.dashboard.state

import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.model.Vessel
import com.example.waterbalancemonitor.domain.usecase.intake.effectiveVolumeMl
import kotlin.math.roundToInt

data class DashboardState(
    val name: String = "",
    val consumedMl: Int = 0,
    val goalMl: Int = 0,
    val todayIntakes: List<IntakeEvent> = emptyList(),
    val drinkTypes: List<DrinkType> = emptyList(),
    val vessels: List<Vessel> = emptyList(),
    val selectedDrinkTypeId: Long? = null,
    val selectedVesselId: Long? = null,
    val volumeMl: Int = DEFAULT_VOLUME_ML,
    val isAddPanelVisible: Boolean = false,
    val isDrinkPickerOpen: Boolean = false,
    val isVesselPickerOpen: Boolean = false
) {
    val remainingMl: Int
        get() = (goalMl - consumedMl).coerceAtLeast(0)

    val progress: Float
        get() = if (goalMl > 0) (consumedMl.toFloat() / goalMl).coerceIn(0f, 1f) else 0f

    val percent: Int
        get() = (progress * 100).roundToInt()

    val selectedDrinkType: DrinkType?
        get() = drinkTypes.firstOrNull { it.id == selectedDrinkTypeId }

    val effectiveVolume: Int
        get() = effectiveVolumeMl(volumeMl, selectedDrinkType?.hydrationCoefficient ?: 1.0)

    companion object {
        const val DEFAULT_VOLUME_ML = 250
        const val MIN_VOLUME_ML = 50
        const val MAX_VOLUME_ML = 2000
        const val VOLUME_STEP = 25
    }
}
