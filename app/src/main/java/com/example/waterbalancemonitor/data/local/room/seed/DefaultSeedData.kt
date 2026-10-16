package com.example.waterbalancemonitor.data.local.room.seed

import com.example.waterbalancemonitor.data.local.room.entity.AchievementEntity
import com.example.waterbalancemonitor.data.local.room.entity.DrinkTypeEntity
import com.example.waterbalancemonitor.data.local.room.entity.VesselEntity
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementConditionType
import com.example.waterbalancemonitor.domain.model.AchievementIcon
import com.example.waterbalancemonitor.domain.model.DrinkTypeIcon
import com.example.waterbalancemonitor.domain.model.VesselIcon

object DefaultSeedData {

    val drinkTypes: List<DrinkTypeEntity> = listOf(
        DrinkTypeEntity(
            name = "Water",
            hydrationCoefficient = 1.0,
            icon = DrinkTypeIcon.WATER.name,
            isDefault = true
        ),
        DrinkTypeEntity(
            name = "Herbal tea",
            hydrationCoefficient = 0.95,
            icon = DrinkTypeIcon.HERBAL_TEA.name,
            isDefault = false
        ),
        DrinkTypeEntity(
            name = "Tea",
            hydrationCoefficient = 0.9,
            icon = DrinkTypeIcon.TEA.name,
            isDefault = false
        ),
        DrinkTypeEntity(
            name = "Milk",
            hydrationCoefficient = 0.87,
            icon = DrinkTypeIcon.MILK.name,
            isDefault = false
        ),
        DrinkTypeEntity(
            name = "Juice",
            hydrationCoefficient = 0.85,
            icon = DrinkTypeIcon.JUICE.name,
            isDefault = false
        ),
        DrinkTypeEntity(
            name = "Coffee",
            hydrationCoefficient = 0.8,
            icon = DrinkTypeIcon.COFFEE.name,
            isDefault = false
        ),
        DrinkTypeEntity(
            name = "Soda",
            hydrationCoefficient = 0.8,
            icon = DrinkTypeIcon.SODA.name,
            isDefault = false
        )
    )

    val achievements: List<AchievementEntity> = listOf(
        AchievementEntity(
            code = "FIRST_GLASS",
            title = "First glass",
            description = "Log your first intake",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 1,
            icon = AchievementIcon.FIRST.name
        ),
        AchievementEntity(
            code = "INTAKE_10",
            title = "Getting started",
            description = "Log 10 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 10,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "FIRST_GLASS"
        ),
        AchievementEntity(
            code = "INTAKE_25",
            title = "Habit forming",
            description = "Log 25 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 25,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_10"
        ),
        AchievementEntity(
            code = "INTAKE_50",
            title = "Fifty sips",
            description = "Log 50 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 50,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_25"
        ),
        AchievementEntity(
            code = "INTAKE_100",
            title = "Centurion",
            description = "Log 100 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 100,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_50"
        ),
        AchievementEntity(
            code = "INTAKE_250",
            title = "Well hydrated",
            description = "Log 250 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 250,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_100"
        ),
        AchievementEntity(
            code = "INTAKE_500",
            title = "Five hundred",
            description = "Log 500 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 500,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_250"
        ),
        AchievementEntity(
            code = "INTAKE_1000",
            title = "Thousand sips",
            description = "Log 1000 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 1000,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_500"
        ),
        AchievementEntity(
            code = "INTAKE_2500",
            title = "Dedicated drinker",
            description = "Log 2500 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 2500,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_1000"
        ),
        AchievementEntity(
            code = "INTAKE_5000",
            title = "Hydration machine",
            description = "Log 5000 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 5000,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_2500"
        ),
        AchievementEntity(
            code = "INTAKE_10000",
            title = "Legendary sips",
            description = "Log 10000 intakes",
            category = AchievementCategory.START.name,
            conditionType = AchievementConditionType.FIRST_INTAKE.name,
            conditionValue = 10000,
            icon = AchievementIcon.FIRST.name,
            prerequisiteCode = "INTAKE_5000"
        ),
        AchievementEntity(
            code = "DAILY_NORM",
            title = "Daily norm",
            description = "Reach your daily goal",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 1,
            icon = AchievementIcon.GOAL.name
        ),
        AchievementEntity(
            code = "NORM_7",
            title = "A good week",
            description = "Reach your goal on 7 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 7,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "DAILY_NORM"
        ),
        AchievementEntity(
            code = "NORM_14",
            title = "Two weeks strong",
            description = "Reach your goal on 14 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 14,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "NORM_7"
        ),
        AchievementEntity(
            code = "NORM_30",
            title = "A month of goals",
            description = "Reach your goal on 30 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 30,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "NORM_14"
        ),
        AchievementEntity(
            code = "NORM_60",
            title = "Two months of goals",
            description = "Reach your goal on 60 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 60,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "NORM_30"
        ),
        AchievementEntity(
            code = "NORM_100",
            title = "Hundred goals",
            description = "Reach your goal on 100 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 100,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "NORM_60"
        ),
        AchievementEntity(
            code = "NORM_180",
            title = "Half a year",
            description = "Reach your goal on 180 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 180,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "NORM_100"
        ),
        AchievementEntity(
            code = "NORM_365",
            title = "A full year",
            description = "Reach your goal on 365 days",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 365,
            icon = AchievementIcon.GOAL.name,
            prerequisiteCode = "NORM_180"
        ),
        AchievementEntity(
            code = "STREAK_3",
            title = "3-day streak",
            description = "Reach the norm 3 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 3,
            icon = AchievementIcon.STREAK.name
        ),
        AchievementEntity(
            code = "STREAK_7",
            title = "Week streak",
            description = "Reach the norm 7 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 7,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_3"
        ),
        AchievementEntity(
            code = "STREAK_14",
            title = "Two-week streak",
            description = "Reach the norm 14 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 14,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_7"
        ),
        AchievementEntity(
            code = "STREAK_30",
            title = "Month streak",
            description = "Reach the norm 30 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 30,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_14"
        ),
        AchievementEntity(
            code = "STREAK_60",
            title = "Two-month streak",
            description = "Reach the norm 60 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 60,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_30"
        ),
        AchievementEntity(
            code = "STREAK_100",
            title = "Hundred-day streak",
            description = "Reach the norm 100 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 100,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_60"
        ),
        AchievementEntity(
            code = "STREAK_180",
            title = "Half-year streak",
            description = "Reach the norm 180 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 180,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_100"
        ),
        AchievementEntity(
            code = "STREAK_365",
            title = "Year-long streak",
            description = "Reach the norm 365 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 365,
            icon = AchievementIcon.STREAK.name,
            prerequisiteCode = "STREAK_180"
        ),
        AchievementEntity(
            code = "TOTAL_1L",
            title = "First liter",
            description = "Drink 1 liter in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 1_000,
            icon = AchievementIcon.VOLUME.name
        ),
        AchievementEntity(
            code = "TOTAL_5L",
            title = "5 liters",
            description = "Drink 5 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 5_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_1L"
        ),
        AchievementEntity(
            code = "TOTAL_10L",
            title = "10 liters",
            description = "Drink 10 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 10_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_5L"
        ),
        AchievementEntity(
            code = "TOTAL_25L",
            title = "25 liters",
            description = "Drink 25 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 25_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_10L"
        ),
        AchievementEntity(
            code = "TOTAL_50L",
            title = "50 liters",
            description = "Drink 50 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 50_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_25L"
        ),
        AchievementEntity(
            code = "TOTAL_100L",
            title = "100 liters",
            description = "Drink 100 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 100_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_50L"
        ),
        AchievementEntity(
            code = "TOTAL_250L",
            title = "250 liters",
            description = "Drink 250 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 250_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_100L"
        ),
        AchievementEntity(
            code = "TOTAL_500L",
            title = "500 liters",
            description = "Drink 500 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 500_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_250L"
        ),
        AchievementEntity(
            code = "TOTAL_1000L",
            title = "1000 liters",
            description = "Drink 1000 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 1_000_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_500L"
        ),
        AchievementEntity(
            code = "TOTAL_2500L",
            title = "2500 liters",
            description = "Drink 2500 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 2_500_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_1000L"
        ),
        AchievementEntity(
            code = "TOTAL_5000L",
            title = "5000 liters",
            description = "Drink 5000 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 5_000_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_2500L"
        ),
        AchievementEntity(
            code = "TOTAL_10000L",
            title = "10000 liters",
            description = "Drink 10000 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 10_000_000,
            icon = AchievementIcon.VOLUME.name,
            prerequisiteCode = "TOTAL_5000L"
        ),
        AchievementEntity(
            code = "EARLY_BIRD",
            title = "Early bird",
            description = "Log water before 8:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_BEFORE_HOUR.name,
            conditionValue = 8,
            icon = AchievementIcon.MORNING.name
        ),
        AchievementEntity(
            code = "EARLY_7",
            title = "Early riser",
            description = "Log water before 7:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_BEFORE_HOUR.name,
            conditionValue = 7,
            icon = AchievementIcon.MORNING.name,
            prerequisiteCode = "EARLY_BIRD"
        ),
        AchievementEntity(
            code = "EARLY_6",
            title = "Dawn drinker",
            description = "Log water before 6:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_BEFORE_HOUR.name,
            conditionValue = 6,
            icon = AchievementIcon.MORNING.name,
            prerequisiteCode = "EARLY_7"
        ),
        AchievementEntity(
            code = "EARLY_5",
            title = "Sunrise sipper",
            description = "Log water before 5:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_BEFORE_HOUR.name,
            conditionValue = 5,
            icon = AchievementIcon.MORNING.name,
            prerequisiteCode = "EARLY_6"
        ),
        AchievementEntity(
            code = "EARLY_4",
            title = "Before the birds",
            description = "Log water before 4:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_BEFORE_HOUR.name,
            conditionValue = 4,
            icon = AchievementIcon.MORNING.name,
            prerequisiteCode = "EARLY_5"
        ),
        AchievementEntity(
            code = "EVENING_20",
            title = "Evening hydration",
            description = "Log water after 20:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_AFTER_HOUR.name,
            conditionValue = 20,
            icon = AchievementIcon.NIGHT.name
        ),
        AchievementEntity(
            code = "NIGHT_OWL",
            title = "Night owl",
            description = "Log water after 22:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_AFTER_HOUR.name,
            conditionValue = 22,
            icon = AchievementIcon.NIGHT.name,
            prerequisiteCode = "EVENING_20"
        ),
        AchievementEntity(
            code = "MIDNIGHT_OWL",
            title = "Midnight owl",
            description = "Log water after 23:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_AFTER_HOUR.name,
            conditionValue = 23,
            icon = AchievementIcon.NIGHT.name,
            prerequisiteCode = "NIGHT_OWL"
        )
    )

    val vessels: List<VesselEntity> = listOf(
        VesselEntity(name = "Glass", volumeMl = 250, icon = VesselIcon.GLASS.name, isDefault = true),
        VesselEntity(name = "Cup", volumeMl = 200, icon = VesselIcon.CUP.name, isDefault = false),
        VesselEntity(name = "Mug", volumeMl = 350, icon = VesselIcon.MUG.name, isDefault = false),
        VesselEntity(name = "Bottle", volumeMl = 500, icon = VesselIcon.BOTTLE.name, isDefault = false),
        VesselEntity(name = "Sport bottle", volumeMl = 750, icon = VesselIcon.SPORT_BOTTLE.name, isDefault = false)
    )
}
