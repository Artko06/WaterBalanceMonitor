package com.example.waterbalancemonitor.data.local.room.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

class LocalTimeHHmmConverter {

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? =
        value?.let { formatter.format(it) }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? =
        value?.let { formatter.parse(it) }

    private companion object {
        val formatter = LocalTime.Format {
            hour()
            char(':')
            minute()
        }
    }
}
