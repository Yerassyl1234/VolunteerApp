package org.example.volunteer.domain.repository

import org.example.volunteer.domain.entity.ReminderType

interface NotificationRepository {
    suspend fun scheduleReminder(
        eventId: String,
        eventTitle: String,
        eventDateMs: Long,
        type: ReminderType)
    suspend fun scheduleAllReminders(eventId: String, eventTitle: String, eventDateMs: Long)
    suspend fun cancelReminder(eventId: String, type: ReminderType)
    suspend fun cancelAllReminders(eventId: String)
}