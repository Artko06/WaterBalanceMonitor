package com.example.waterbalancemonitor.fake

import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.domain.model.UserAchievement
import com.example.waterbalancemonitor.domain.repository.AchievementRepository
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import com.example.waterbalancemonitor.domain.repository.ProfileRepository
import com.example.waterbalancemonitor.domain.repository.UserAchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class FakeDailyGoalRepository : DailyGoalRepository {

    private val goals = mutableMapOf<LocalDate, DailyGoal>()

    override fun observeByDate(date: LocalDate): Flow<DailyGoal?> =
        flowOf(goals[date])

    override suspend fun getByDate(date: LocalDate): DailyGoal? = goals[date]

    override fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailyGoal>> =
        flowOf(goals.values.filter { it.date in from..to })

    override suspend fun save(goal: DailyGoal) {
        val stored = if (goal.id == 0L) goal.copy(id = (goals.size + 1).toLong()) else goal
        goals[goal.date] = stored
    }

    override suspend fun setCompleted(date: LocalDate, isCompleted: Boolean) {
        goals[date]?.let { goals[date] = it.copy(isCompleted = isCompleted) }
    }

    override suspend fun deleteByDate(date: LocalDate) {
        goals.remove(date)
    }
}

class FakeDailySummaryRepository : DailySummaryRepository {

    private val summaries = mutableMapOf<LocalDate, DailySummary>()

    fun put(summary: DailySummary) {
        summaries[summary.date] = summary
    }

    override fun observeByDate(date: LocalDate): Flow<DailySummary?> =
        flowOf(summaries[date])

    override suspend fun getByDate(date: LocalDate): DailySummary? = summaries[date]

    override fun observeBetween(from: LocalDate, to: LocalDate): Flow<List<DailySummary>> =
        flowOf(summaries.values.filter { it.date in from..to })

    override suspend fun getBetween(from: LocalDate, to: LocalDate): List<DailySummary> =
        summaries.values.filter { it.date in from..to }

    override suspend fun save(summary: DailySummary) {
        summaries[summary.date] = summary
    }

    override suspend fun deleteByDate(date: LocalDate) {
        summaries.remove(date)
    }
}

class FakeProfileRepository : ProfileRepository {

    var profile: Profile? = null

    override fun observeProfile(): Flow<Profile?> = flowOf(profile)

    override suspend fun getProfile(): Profile? = profile

    override suspend fun saveProfile(profile: Profile) {
        this.profile = profile
    }

    override suspend fun deleteProfile() {
        profile = null
    }
}

class FakeIntakeEventRepository : IntakeEventRepository {

    private val events = mutableListOf<IntakeEvent>()

    var lastFrom: LocalDateTime? = null
    var lastTo: LocalDateTime? = null

    fun add(event: IntakeEvent) {
        events.add(event)
    }

    override fun observeAll(): Flow<List<IntakeEvent>> = flowOf(events.toList())

    override fun observeBetween(from: LocalDateTime, to: LocalDateTime): Flow<List<IntakeEvent>> {
        lastFrom = from
        lastTo = to
        return flowOf(events.filter { it.consumedAt in from..to })
    }

    override suspend fun getBetween(from: LocalDateTime, to: LocalDateTime): List<IntakeEvent> =
        events.filter { it.consumedAt in from..to }

    override suspend fun getById(id: Long): IntakeEvent? = null

    override suspend fun save(event: IntakeEvent) = Unit

    override suspend fun saveAll(events: List<IntakeEvent>) = Unit

    override suspend fun deleteById(id: Long) = Unit

    override suspend fun sumVolumeBetween(from: LocalDateTime, to: LocalDateTime): Int = 0

    override suspend fun countBetween(from: LocalDateTime, to: LocalDateTime): Int = 0
}

class FakeAchievementRepository : AchievementRepository {

    private val achievements = mutableListOf<Achievement>()

    fun add(achievement: Achievement) {
        achievements.add(achievement)
    }

    override fun observeAll(): Flow<List<Achievement>> = flowOf(achievements.toList())

    override suspend fun getAll(): List<Achievement> = achievements.toList()

    override suspend fun getByCode(code: String): Achievement? =
        achievements.firstOrNull { it.code == code }

    override suspend fun save(achievement: Achievement) {
        achievements.add(achievement)
    }

    override suspend fun saveAll(achievements: List<Achievement>) {
        this.achievements.addAll(achievements)
    }
}

class FakeUserAchievementRepository : UserAchievementRepository {

    private val byAchievementId = mutableMapOf<Long, UserAchievement>()

    override fun observeAll(): Flow<List<UserAchievement>> =
        flowOf(byAchievementId.values.toList())

    override fun observeByAchievementId(achievementId: Long): Flow<UserAchievement?> =
        flowOf(byAchievementId[achievementId])

    override suspend fun getByAchievementId(achievementId: Long): UserAchievement? =
        byAchievementId[achievementId]

    override suspend fun save(userAchievement: UserAchievement) {
        byAchievementId[userAchievement.achievementId] = userAchievement
    }

    override suspend fun saveAll(userAchievements: List<UserAchievement>) {
        userAchievements.forEach { byAchievementId[it.achievementId] = it }
    }

    override suspend fun deleteByAchievementId(achievementId: Long) {
        byAchievementId.remove(achievementId)
    }
}
