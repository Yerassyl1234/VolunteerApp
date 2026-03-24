package org.example.volunteer.domain.repository

import org.example.volunteer.domain.entity.ReminderType

interface NotificationRepository {
    suspend fun cancelReminder(eventId: String)
    suspend fun scheduleReminder(eventId: String, type: ReminderType)
}