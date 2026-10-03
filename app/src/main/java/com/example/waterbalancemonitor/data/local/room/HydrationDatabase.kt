package com.example.waterbalancemonitor.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.waterbalancemonitor.data.local.room.converter.LocalDateIsoConverter
import com.example.waterbalancemonitor.data.local.room.converter.LocalTimeHHmmConverter
import com.example.waterbalancemonitor.data.local.room.dao.AchievementDao
import com.example.waterbalancemonitor.data.local.room.dao.DailyGoalDao
import com.example.waterbalancemonitor.data.local.room.dao.DailySummaryDao
import com.example.waterbalancemonitor.data.local.room.dao.DrinkTypeDao
import com.example.waterbalancemonitor.data.local.room.dao.IntakeEventDao
import com.example.waterbalancemonitor.data.local.room.dao.ProfileDao
import com.example.waterbalancemonitor.data.local.room.dao.ReminderDao
import com.example.waterbalancemonitor.data.local.room.dao.UserAchievementDao
import com.example.waterbalancemonitor.data.local.room.dao.VesselDao
import com.example.waterbalancemonitor.data.local.room.entity.AchievementEntity
import com.example.waterbalancemonitor.data.local.room.entity.DailyGoalEntity
import com.example.waterbalancemonitor.data.local.room.entity.DailySummaryEntity
import com.example.waterbalancemonitor.data.local.room.entity.DrinkTypeEntity
import com.example.waterbalancemonitor.data.local.room.entity.IntakeEventEntity
import com.example.waterbalancemonitor.data.local.room.entity.ProfileEntity
import com.example.waterbalancemonitor.data.local.room.entity.ReminderEntity
import com.example.waterbalancemonitor.data.local.room.entity.UserAchievementEntity
import com.example.waterbalancemonitor.data.local.room.entity.VesselEntity

@Database(
    entities = [
        ProfileEntity::class,
        DrinkTypeEntity::class,
        VesselEntity::class,
        IntakeEventEntity::class,
        DailyGoalEntity::class,
        DailySummaryEntity::class,
        ReminderEntity::class,
        AchievementEntity::class,
        UserAchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalTimeHHmmConverter::class, LocalDateIsoConverter::class)
abstract class HydrationDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    abstract fun drinkTypeDao(): DrinkTypeDao

    abstract fun vesselDao(): VesselDao

    abstract fun intakeEventDao(): IntakeEventDao

    abstract fun dailyGoalDao(): DailyGoalDao

    abstract fun dailySummaryDao(): DailySummaryDao

    abstract fun reminderDao(): ReminderDao

    abstract fun achievementDao(): AchievementDao

    abstract fun userAchievementDao(): UserAchievementDao

    companion object {
        const val DATABASE_NAME = "hydration.db"
    }
}
