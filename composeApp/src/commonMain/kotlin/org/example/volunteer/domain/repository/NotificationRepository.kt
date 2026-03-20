package org.example.volunteer.domain.repository

interface NotificationRepository {
    fun cancelReminder(eventId:String)
}