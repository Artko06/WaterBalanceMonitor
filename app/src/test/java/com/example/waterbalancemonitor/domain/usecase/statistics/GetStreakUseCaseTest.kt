package com.example.waterbalancemonitor.domain.usecase.statistics

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.fake.FakeDailyGoalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Clock

class GetStreakUseCaseTest {

    private val repository = FakeDailyGoalRepository()
    private val useCase = GetStreakUseCase(repository)
    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    private fun goal(daysAgo: Int, completed: Boolean) = DailyGoal(
        id = 0,
        date = today.plus(DatePeriod(days = -daysAgo)),
        targetMl = 2000,
        baseNormMl = 2000,
        bonusMl = 0,
        isCompleted = completed
    )

    @Test
    fun `counts current streak ending yesterday when today is not completed`() = runBlocking {
        repository.save(goal(daysAgo = 1, completed = true))
        repository.save(goal(daysAgo = 2, completed = true))
        repository.save(goal(daysAgo = 3, completed = true))
        repository.save(goal(daysAgo = 4, completed = false))

        val streak = useCase().first()

        assertEquals(3, streak.current)
        assertEquals(3, streak.longest)
    }

    @Test
    fun `includes today when today is completed`() = runBlocking {
        repository.save(goal(daysAgo = 0, completed = true))
        repository.save(goal(daysAgo = 1, completed = true))
        repository.save(goal(daysAgo = 2, completed = false))

        val streak = useCase().first()

        assertEquals(2, streak.current)
        assertEquals(2, streak.longest)
    }

    @Test
    fun `longest streak is the max run across history`() = runBlocking {
        repository.save(goal(daysAgo = 1, completed = true))
        repository.save(goal(daysAgo = 2, completed = false))
        repository.save(goal(daysAgo = 3, completed = true))
        repository.save(goal(daysAgo = 4, completed = true))
        repository.save(goal(daysAgo = 5, completed = true))
        repository.save(goal(daysAgo = 6, completed = true))

        val streak = useCase().first()

        assertEquals(1, streak.current)
        assertEquals(4, streak.longest)
    }
}
