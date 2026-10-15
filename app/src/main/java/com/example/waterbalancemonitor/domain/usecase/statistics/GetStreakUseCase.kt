package com.example.waterbalancemonitor.domain.usecase.statistics

import com.example.waterbalancemonitor.domain.model.Streak
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class GetStreakUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) {
    operator fun invoke(): Flow<Streak> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val from = today.plus(DatePeriod(days = -WINDOW_DAYS))
        return dailyGoalRepository.observeBetween(from, today).map { goals ->
            computeStreak(goals.map { it.date to it.isCompleted }, today)
        }
    }

    private fun computeStreak(entries: List<Pair<LocalDate, Boolean>>, today: LocalDate): Streak {
        val completed = entries.filter { it.second }.map { it.first }.toSet()

        var current = 0
        var cursor = if (completed.contains(today)) today else today.plus(DatePeriod(days = -1))
        while (completed.contains(cursor)) {
            current++
            cursor = cursor.plus(DatePeriod(days = -1))
        }

        var longest = 0
        var run = 0
        var previous: LocalDate? = null
        for (date in completed.sorted()) {
            run = if (previous != null && date == previous.plus(DatePeriod(days = 1))) run + 1 else 1
            if (run > longest) longest = run
            previous = date
        }

        return Streak(current = current, longest = longest)
    }

    private companion object {
        const val WINDOW_DAYS = 365
    }
}
