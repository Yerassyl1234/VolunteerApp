package org.example.volunteer.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(
    private val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val eventId = inputData.getString(KEY_EVENT_ID) ?: return Result.failure()
        val eventTitle = inputData.getString(KEY_EVENT_TITLE) ?: return Result.failure()
        val reminderType = inputData.getString(KEY_REMINDER_TYPE) ?: return Result.failure()

        val message = when (reminderType) {
            "DAY_BEFORE" -> "Завтра состоится: $eventTitle"
            "TWO_HOURS_BEFORE" -> "Через 2 часа: $eventTitle"
            else -> "Скоро: $eventTitle"
        }

        showNotification(eventId, eventTitle, message)
        return Result.success()
    }

    private fun showNotification(eventId: String, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Напоминания о событиях",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Напоминания о предстоящих волонтёрских событиях"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(eventId.hashCode(), notification)
    }

    companion object {
        const val CHANNEL_ID = "event_reminders"
        const val KEY_EVENT_ID = "event_id"
        const val KEY_EVENT_TITLE = "event_title"
        const val KEY_REMINDER_TYPE = "reminder_type"
    }
}