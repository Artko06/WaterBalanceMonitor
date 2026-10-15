package com.example.waterbalancemonitor.presentation.screens.statistics.state

import androidx.annotation.StringRes
import com.example.waterbalancemonitor.R
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

enum class StatsPeriod(
    val days: Int,
    @param:StringRes val labelRes: Int
) {
    DAY(1, R.string.stats_period_day),
    WEEK(7, R.string.stats_period_week),
    MONTH(30, R.string.stats_period_month),
    YEAR(365, R.string.stats_period_year);

    fun currentRange(today: LocalDate): Pair<LocalDate, LocalDate> {
        val from = today.plus(DatePeriod(days = -(days - 1)))
        return from to today
    }

    fun previousRange(today: LocalDate): Pair<LocalDate, LocalDate> {
        val to = today.plus(DatePeriod(days = -days))
        val from = to.plus(DatePeriod(days = -(days - 1)))
        return from to to
    }
}
