package org.example.volunteer.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import org.example.volunteer.data.notification.ReminderWorker
import org.example.volunteer.domain.entity.ReminderType
import org.example.volunteer.domain.repository.NotificationRepository
import java.util.concurrent.TimeUnit

class NotificationRepositoryImpl(
    private val context: Context,
) : NotificationRepository {

    override suspend fun scheduleReminder(
        eventId: String,
        eventTitle: String,
        eventDateMs: Long,
        type: ReminderType,
    ) {
        val now = System.currentTimeMillis()
        val triggerAtMs = eventDateMs - (type.hoursBeforeEvent * 60 * 60 * 1000)
        val delayMs = triggerAtMs - now

        if (delayMs <= 0) return

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    ReminderWorker.KEY_EVENT_ID to eventId,
                    ReminderWorker.KEY_EVENT_TITLE to eventTitle,
                    ReminderWorker.KEY_REMINDER_TYPE to type.name,
                )
            )
            .addTag(buildTag(eventId, type))
            .build()

        WorkManager.getInstance(context)
            .enqueue(workRequest)
    }

    override suspend fun scheduleAllReminders(
        eventId: String,
        eventTitle: String,
        eventDateMs: Long,
    ) {
        ReminderType.entries.forEach { type ->
            scheduleReminder(eventId, eventTitle, eventDateMs, type)
        }
    }

    override suspend fun cancelReminder(eventId: String, type: ReminderType) {
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(buildTag(eventId, type))
    }

    override suspend fun cancelAllReminders(eventId: String) {
        ReminderType.entries.forEach { type ->
            cancelReminder(eventId, type)
        }
    }

    private fun buildTag(eventId: String, type: ReminderType): String =
        "reminder_${eventId}_${type.name}"
}