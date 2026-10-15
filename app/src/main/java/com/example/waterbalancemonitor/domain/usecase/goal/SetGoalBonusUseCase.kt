package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class SetGoalBonusUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository,
    private val dailySummaryRepository: DailySummaryRepository,
    private val profileRepository: ProfileRepository,
    private val ensureDailyGoal: EnsureDailyGoalUseCase
) {
    suspend operator fun invoke(date: LocalDate, bonusMl: Int) {
        var goal = dailyGoalRepository.getByDate(date)
        if (goal == null) {
            val baseNormMl = profileRepository.getProfile()?.dailyNormMl ?: return
            ensureDailyGoal(date, baseNormMl)
            goal = dailyGoalRepository.getByDate(date) ?: return
        }
        val bonus = bonusMl.coerceIn(MIN_BONUS_ML, MAX_BONUS_ML)
        val targetMl = goal.baseNormMl + bonus
        val totalMl = dailySummaryRepository.getByDate(date)?.totalMl ?: 0
        dailyGoalRepository.save(
            goal.copy(
                targetMl = targetMl,
                bonusMl = bonus,
                isCompleted = totalMl >= targetMl
            )
        )
    }

    private companion object {
        const val MIN_BONUS_ML = 0
        const val MAX_BONUS_ML = 2000
    }
}
