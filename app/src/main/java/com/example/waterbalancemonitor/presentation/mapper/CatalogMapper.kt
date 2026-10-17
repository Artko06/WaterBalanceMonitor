package com.example.waterbalancemonitor.presentation.mapper

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.DrinkType
import com.example.waterbalancemonitor.domain.model.DrinkTypeIcon
import com.example.waterbalancemonitor.domain.model.Vessel
import com.example.waterbalancemonitor.domain.model.VesselIcon

@StringRes
fun DrinkTypeIcon.nameRes(): Int? = when (this) {
    DrinkTypeIcon.WATER -> R.string.drink_water
    DrinkTypeIcon.HERBAL_TEA -> R.string.drink_herbal_tea
    DrinkTypeIcon.TEA -> R.string.drink_tea
    DrinkTypeIcon.MILK -> R.string.drink_milk
    DrinkTypeIcon.JUICE -> R.string.drink_juice
    DrinkTypeIcon.COFFEE -> R.string.drink_coffee
    DrinkTypeIcon.SODA -> R.string.drink_soda
    DrinkTypeIcon.SOUP, DrinkTypeIcon.DEFAULT -> null
}

@StringRes
fun VesselIcon.nameRes(): Int? = when (this) {
    VesselIcon.GLASS -> R.string.vessel_glass
    VesselIcon.CUP -> R.string.vessel_cup
    VesselIcon.MUG -> R.string.vessel_mug
    VesselIcon.BOTTLE -> R.string.vessel_bottle
    VesselIcon.SPORT_BOTTLE -> R.string.vessel_sport_bottle
    VesselIcon.DEFAULT -> null
}

@Composable
fun drinkName(drinkType: DrinkType): String =
    drinkType.icon.nameRes()?.let { stringResource(it) } ?: drinkType.name

@Composable
fun vesselName(vessel: Vessel): String =
    vessel.icon.nameRes()?.let { stringResource(it) } ?: vessel.name

@StringRes
private fun achievementTitleRes(code: String): Int? = when (code) {
    "FIRST_GLASS" -> R.string.achv_first_glass_title
    "INTAKE_10" -> R.string.achv_intake_10_title
    "INTAKE_25" -> R.string.achv_intake_25_title
    "INTAKE_50" -> R.string.achv_intake_50_title
    "INTAKE_100" -> R.string.achv_intake_100_title
    "INTAKE_250" -> R.string.achv_intake_250_title
    "INTAKE_500" -> R.string.achv_intake_500_title
    "INTAKE_1000" -> R.string.achv_intake_1000_title
    "INTAKE_2500" -> R.string.achv_intake_2500_title
    "INTAKE_5000" -> R.string.achv_intake_5000_title
    "INTAKE_10000" -> R.string.achv_intake_10000_title
    "DAILY_NORM" -> R.string.achv_daily_norm_title
    "NORM_7" -> R.string.achv_norm_7_title
    "NORM_14" -> R.string.achv_norm_14_title
    "NORM_30" -> R.string.achv_norm_30_title
    "NORM_60" -> R.string.achv_norm_60_title
    "NORM_100" -> R.string.achv_norm_100_title
    "NORM_180" -> R.string.achv_norm_180_title
    "NORM_365" -> R.string.achv_norm_365_title
    "STREAK_3" -> R.string.achv_streak_3_title
    "STREAK_7" -> R.string.achv_streak_7_title
    "STREAK_14" -> R.string.achv_streak_14_title
    "STREAK_30" -> R.string.achv_streak_30_title
    "STREAK_60" -> R.string.achv_streak_60_title
    "STREAK_100" -> R.string.achv_streak_100_title
    "STREAK_180" -> R.string.achv_streak_180_title
    "STREAK_365" -> R.string.achv_streak_365_title
    "TOTAL_1L" -> R.string.achv_total_1l_title
    "TOTAL_5L" -> R.string.achv_total_5l_title
    "TOTAL_10L" -> R.string.achv_total_10l_title
    "TOTAL_25L" -> R.string.achv_total_25l_title
    "TOTAL_50L" -> R.string.achv_total_50l_title
    "TOTAL_100L" -> R.string.achv_total_100l_title
    "TOTAL_250L" -> R.string.achv_total_250l_title
    "TOTAL_500L" -> R.string.achv_total_500l_title
    "TOTAL_1000L" -> R.string.achv_total_1000l_title
    "TOTAL_2500L" -> R.string.achv_total_2500l_title
    "TOTAL_5000L" -> R.string.achv_total_5000l_title
    "TOTAL_10000L" -> R.string.achv_total_10000l_title
    "EARLY_BIRD" -> R.string.achv_early_bird_title
    "EARLY_7" -> R.string.achv_early_7_title
    "EARLY_6" -> R.string.achv_early_6_title
    "EARLY_5" -> R.string.achv_early_5_title
    "EARLY_4" -> R.string.achv_early_4_title
    "EVENING_20" -> R.string.achv_evening_20_title
    "NIGHT_OWL" -> R.string.achv_night_owl_title
    "MIDNIGHT_OWL" -> R.string.achv_midnight_owl_title
    else -> null
}

@StringRes
private fun achievementDescRes(code: String): Int? = when (code) {
    "FIRST_GLASS" -> R.string.achv_first_glass_desc
    "INTAKE_10" -> R.string.achv_intake_10_desc
    "INTAKE_25" -> R.string.achv_intake_25_desc
    "INTAKE_50" -> R.string.achv_intake_50_desc
    "INTAKE_100" -> R.string.achv_intake_100_desc
    "INTAKE_250" -> R.string.achv_intake_250_desc
    "INTAKE_500" -> R.string.achv_intake_500_desc
    "INTAKE_1000" -> R.string.achv_intake_1000_desc
    "INTAKE_2500" -> R.string.achv_intake_2500_desc
    "INTAKE_5000" -> R.string.achv_intake_5000_desc
    "INTAKE_10000" -> R.string.achv_intake_10000_desc
    "DAILY_NORM" -> R.string.achv_daily_norm_desc
    "NORM_7" -> R.string.achv_norm_7_desc
    "NORM_14" -> R.string.achv_norm_14_desc
    "NORM_30" -> R.string.achv_norm_30_desc
    "NORM_60" -> R.string.achv_norm_60_desc
    "NORM_100" -> R.string.achv_norm_100_desc
    "NORM_180" -> R.string.achv_norm_180_desc
    "NORM_365" -> R.string.achv_norm_365_desc
    "STREAK_3" -> R.string.achv_streak_3_desc
    "STREAK_7" -> R.string.achv_streak_7_desc
    "STREAK_14" -> R.string.achv_streak_14_desc
    "STREAK_30" -> R.string.achv_streak_30_desc
    "STREAK_60" -> R.string.achv_streak_60_desc
    "STREAK_100" -> R.string.achv_streak_100_desc
    "STREAK_180" -> R.string.achv_streak_180_desc
    "STREAK_365" -> R.string.achv_streak_365_desc
    "TOTAL_1L" -> R.string.achv_total_1l_desc
    "TOTAL_5L" -> R.string.achv_total_5l_desc
    "TOTAL_10L" -> R.string.achv_total_10l_desc
    "TOTAL_25L" -> R.string.achv_total_25l_desc
    "TOTAL_50L" -> R.string.achv_total_50l_desc
    "TOTAL_100L" -> R.string.achv_total_100l_desc
    "TOTAL_250L" -> R.string.achv_total_250l_desc
    "TOTAL_500L" -> R.string.achv_total_500l_desc
    "TOTAL_1000L" -> R.string.achv_total_1000l_desc
    "TOTAL_2500L" -> R.string.achv_total_2500l_desc
    "TOTAL_5000L" -> R.string.achv_total_5000l_desc
    "TOTAL_10000L" -> R.string.achv_total_10000l_desc
    "EARLY_BIRD" -> R.string.achv_early_bird_desc
    "EARLY_7" -> R.string.achv_early_7_desc
    "EARLY_6" -> R.string.achv_early_6_desc
    "EARLY_5" -> R.string.achv_early_5_desc
    "EARLY_4" -> R.string.achv_early_4_desc
    "EVENING_20" -> R.string.achv_evening_20_desc
    "NIGHT_OWL" -> R.string.achv_night_owl_desc
    "MIDNIGHT_OWL" -> R.string.achv_midnight_owl_desc
    else -> null
}

@Composable
fun achievementTitle(code: String, fallback: String): String =
    achievementTitleRes(code)?.let { stringResource(it) } ?: fallback

@Composable
fun achievementDescription(code: String, fallback: String): String =
    achievementDescRes(code)?.let { stringResource(it) } ?: fallback
