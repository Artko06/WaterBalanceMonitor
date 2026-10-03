package com.example.waterbalancemonitor.di

import android.content.Context
import androidx.room.Room
import com.example.waterbalancemonitor.data.local.room.HydrationDatabase
import com.example.waterbalancemonitor.data.local.room.dao.AchievementDao
import com.example.waterbalancemonitor.data.local.room.dao.DailyGoalDao
import com.example.waterbalancemonitor.data.local.room.dao.DailySummaryDao
import com.example.waterbalancemonitor.data.local.room.dao.DrinkTypeDao
import com.example.waterbalancemonitor.data.local.room.dao.IntakeEventDao
import com.example.waterbalancemonitor.data.local.room.dao.ProfileDao
import com.example.waterbalancemonitor.data.local.room.dao.ReminderDao
import com.example.waterbalancemonitor.data.local.room.dao.UserAchievementDao
import com.example.waterbalancemonitor.data.local.room.dao.VesselDao
import com.example.waterbalancemonitor.data.local.room.seed.DatabaseSeedCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHydrationDatabase(
        @ApplicationContext context: Context
    ): HydrationDatabase = Room.databaseBuilder(
        context,
        HydrationDatabase::class.java,
        HydrationDatabase.DATABASE_NAME
    ).addCallback(DatabaseSeedCallback).build()

    @Provides
    fun provideProfileDao(database: HydrationDatabase): ProfileDao = database.profileDao()

    @Provides
    fun provideDrinkTypeDao(database: HydrationDatabase): DrinkTypeDao = database.drinkTypeDao()

    @Provides
    fun provideVesselDao(database: HydrationDatabase): VesselDao = database.vesselDao()

    @Provides
    fun provideIntakeEventDao(database: HydrationDatabase): IntakeEventDao = database.intakeEventDao()

    @Provides
    fun provideDailyGoalDao(database: HydrationDatabase): DailyGoalDao = database.dailyGoalDao()

    @Provides
    fun provideDailySummaryDao(database: HydrationDatabase): DailySummaryDao = database.dailySummaryDao()

    @Provides
    fun provideReminderDao(database: HydrationDatabase): ReminderDao = database.reminderDao()

    @Provides
    fun provideAchievementDao(database: HydrationDatabase): AchievementDao = database.achievementDao()

    @Provides
    fun provideUserAchievementDao(database: HydrationDatabase): UserAchievementDao =
        database.userAchievementDao()
}
