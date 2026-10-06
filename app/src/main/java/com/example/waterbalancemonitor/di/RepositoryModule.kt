package com.example.waterbalancemonitor.di

import com.example.waterbalancemonitor.data.repository.AchievementRepositoryImpl
import com.example.waterbalancemonitor.data.repository.DailyGoalRepositoryImpl
import com.example.waterbalancemonitor.data.repository.DailySummaryRepositoryImpl
import com.example.waterbalancemonitor.data.repository.DrinkTypeRepositoryImpl
import com.example.waterbalancemonitor.data.repository.IntakeEventRepositoryImpl
import com.example.waterbalancemonitor.data.repository.ProfileRepositoryImpl
import com.example.waterbalancemonitor.data.repository.ReminderRepositoryImpl
import com.example.waterbalancemonitor.data.repository.SettingsRepositoryImpl
import com.example.waterbalancemonitor.data.repository.UserAchievementRepositoryImpl
import com.example.waterbalancemonitor.data.repository.VesselRepositoryImpl
import com.example.waterbalancemonitor.domain.repository.AchievementRepository
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import com.example.waterbalancemonitor.domain.repository.DrinkTypeRepository
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import com.example.waterbalancemonitor.domain.repository.ReminderRepository
import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import com.example.waterbalancemonitor.domain.repository.UserAchievementRepository
import com.example.waterbalancemonitor.domain.repository.VesselRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindDrinkTypeRepository(impl: DrinkTypeRepositoryImpl): DrinkTypeRepository

    @Binds
    @Singleton
    abstract fun bindVesselRepository(impl: VesselRepositoryImpl): VesselRepository

    @Binds
    @Singleton
    abstract fun bindIntakeEventRepository(impl: IntakeEventRepositoryImpl): IntakeEventRepository

    @Binds
    @Singleton
    abstract fun bindDailyGoalRepository(impl: DailyGoalRepositoryImpl): DailyGoalRepository

    @Binds
    @Singleton
    abstract fun bindDailySummaryRepository(
        impl: DailySummaryRepositoryImpl
    ): DailySummaryRepository

    @Binds
    @Singleton
    abstract fun bindReminderRepository(impl: ReminderRepositoryImpl): ReminderRepository

    @Binds
    @Singleton
    abstract fun bindAchievementRepository(impl: AchievementRepositoryImpl): AchievementRepository

    @Binds
    @Singleton
    abstract fun bindUserAchievementRepository(
        impl: UserAchievementRepositoryImpl
    ): UserAchievementRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
