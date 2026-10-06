package com.example.waterbalancemonitor.presentation.mapper

import androidx.annotation.DrawableRes
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.VesselIcon

@DrawableRes
fun VesselIcon.toDrawableRes(): Int = when (this) {
    VesselIcon.GLASS -> R.drawable.ic_vessel_glass
    VesselIcon.BOTTLE -> R.drawable.ic_vessel_bottle
    VesselIcon.CUP -> R.drawable.ic_vessel_cup
    VesselIcon.MUG -> R.drawable.ic_vessel_mug
    VesselIcon.SPORT_BOTTLE -> R.drawable.ic_vessel_sport_bottle
    VesselIcon.DEFAULT -> R.drawable.ic_vessel_default
}
