package com.example.waterbalancemonitor.data.local.room.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class LocalDateIsoConverter {

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? =
        value?.let { formatter.format(it) }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? =
        value?.let { formatter.parse(it) }

    private companion object {
        val formatter = LocalDate.Formats.ISO
    }
}
