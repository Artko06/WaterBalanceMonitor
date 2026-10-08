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
            code = "DAILY_NORM",
            title = "Daily norm",
            description = "Reach your daily goal",
            category = AchievementCategory.DAILY.name,
            conditionType = AchievementConditionType.DAILY_NORM_REACHED.name,
            conditionValue = 1,
            icon = AchievementIcon.GOAL.name
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
            icon = AchievementIcon.STREAK.name
        ),
        AchievementEntity(
            code = "STREAK_30",
            title = "Month streak",
            description = "Reach the norm 30 days in a row",
            category = AchievementCategory.STREAK.name,
            conditionType = AchievementConditionType.STREAK_DAYS.name,
            conditionValue = 30,
            icon = AchievementIcon.STREAK.name
        ),
        AchievementEntity(
            code = "TOTAL_100L",
            title = "100 liters",
            description = "Drink 100 liters in total",
            category = AchievementCategory.TOTAL.name,
            conditionType = AchievementConditionType.TOTAL_VOLUME_ML.name,
            conditionValue = 100_000,
            icon = AchievementIcon.VOLUME.name
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
            code = "NIGHT_OWL",
            title = "Night owl",
            description = "Log water after 22:00",
            category = AchievementCategory.TIME.name,
            conditionType = AchievementConditionType.INTAKE_AFTER_HOUR.name,
            conditionValue = 22,
            icon = AchievementIcon.NIGHT.name
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
