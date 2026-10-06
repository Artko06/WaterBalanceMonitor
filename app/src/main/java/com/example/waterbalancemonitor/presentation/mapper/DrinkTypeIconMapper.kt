package com.example.waterbalancemonitor.presentation.mapper

import androidx.annotation.DrawableRes
import com.example.waterbalancemonitor.R
import com.example.waterbalancemonitor.domain.model.DrinkTypeIcon

@DrawableRes
fun DrinkTypeIcon.toDrawableRes(): Int = when (this) {
    DrinkTypeIcon.WATER -> R.drawable.ic_drink_water
    DrinkTypeIcon.HERBAL_TEA -> R.drawable.ic_drink_herbal_tea
    DrinkTypeIcon.TEA -> R.drawable.ic_drink_tea
    DrinkTypeIcon.SOUP -> R.drawable.ic_drink_soup
    DrinkTypeIcon.MILK -> R.drawable.ic_drink_milk
    DrinkTypeIcon.JUICE -> R.drawable.ic_drink_juice
    DrinkTypeIcon.COFFEE -> R.drawable.ic_drink_coffee
    DrinkTypeIcon.SODA -> R.drawable.ic_drink_soda
    DrinkTypeIcon.DEFAULT -> R.drawable.ic_drink_default
}
