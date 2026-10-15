package com.example.waterbalancemonitor.presentation.screens.history.state

import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.model.Vessel
import com.example.waterbalancemonitor.domain.usecase.intake.effectiveVolumeMl
import kotlinx.datetime.LocalDate
import kotlin.math.roundToInt

data class HistoryState(
    val selectedDate: LocalDate = LocalDate(1970, 1, 1),
    val isToday: Boolean = true,
    val events: List<IntakeEvent> = emptyList(),
    val drinkTypes: List<DrinkType> = emptyList(),
    val vessels: List<Vessel> = emptyList(),
    val dayTotalMl: Int = 0,
    val goalTargetMl: Int = 0,
    val isCompleted: Boolean = false,
    val editingEvent: IntakeEvent? = null,
    val editDrinkTypeId: Long? = null,
    val editVesselId: Long? = null,
    val editVolumeMl: Int = DEFAULT_VOLUME_ML,
    val editHour: Int = 0,
    val editMinute: Int = 0,
    val isDrinkPickerOpen: Boolean = false,
    val isVesselPickerOpen: Boolean = false,
    val isLoading: Boolean = true
) {
    val isEditing: Boolean
        get() = editingEvent != null

    val editDrinkType: DrinkType?
        get() = drinkTypes.firstOrNull { it.id == editDrinkTypeId }

    val editVessel: Vessel?
        get() = vessels.firstOrNull { it.id == editVesselId }

    val editEffectiveVolume: Int
        get() = effectiveVolumeMl(editVolumeMl, editDrinkType?.hydrationCoefficient ?: 1.0)

    val progress: Float
        get() = if (goalTargetMl > 0) {
            (dayTotalMl.toFloat() / goalTargetMl).coerceIn(0f, 1f)
        } else {
            0f
        }

    val percent: Int
        get() = (progress * 100).roundToInt()

    companion object {
        const val DEFAULT_VOLUME_ML = 250
        const val MIN_VOLUME_ML = 50
        const val MAX_VOLUME_ML = 2000
        const val VOLUME_STEP = 25
    }
}
