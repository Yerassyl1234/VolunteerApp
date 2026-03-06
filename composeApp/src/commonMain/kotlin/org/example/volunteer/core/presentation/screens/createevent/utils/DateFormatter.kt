package com.example.volunteerapp.presentation.screens.createevent.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

fun LocalDate?.formatForUI(): String {
    if (this == null) return "Выберите дату"
    return "${dayOfMonth.toString().padStart(2, '0')}.${monthNumber.toString().padStart(2, '0')}.$year"
}

fun LocalTime?.formatForUI(): String {
    if (this == null) return "Выберите время"
    return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
}

fun LocalDate.toMillisUTC(): Long {
    return this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
}