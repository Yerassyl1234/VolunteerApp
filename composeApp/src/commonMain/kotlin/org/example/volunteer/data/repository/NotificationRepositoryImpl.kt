package org.example.volunteer.data.repository

import org.example.volunteer.domain.entity.ReminderType
import org.example.volunteer.domain.repository.NotificationRepository

class NotificationRepositoryImpl : NotificationRepository {
    override suspend fun cancelReminder(eventId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun scheduleReminder(
        eventId: String,
        type: ReminderType
    ) {
        TODO("Not yet implemented")
    }
}