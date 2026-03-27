package org.example.volunteer.domain.entity

enum class ReminderType(val hoursBeforeEvent: Long) {
    DAY_BEFORE(hoursBeforeEvent = 24),
    TWO_HOURS_BEFORE(hoursBeforeEvent = 2),
}