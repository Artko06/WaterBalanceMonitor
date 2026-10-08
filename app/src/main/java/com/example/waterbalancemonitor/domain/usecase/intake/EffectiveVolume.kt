package com.example.waterbalancemonitor.domain.usecase.intake

import kotlin.math.roundToInt

fun effectiveVolumeMl(volumeMl: Int, hydrationCoefficient: Double): Int =
    (volumeMl * hydrationCoefficient).roundToInt()
