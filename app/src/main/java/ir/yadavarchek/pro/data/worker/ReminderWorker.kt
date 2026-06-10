package ir.yadavarchek.pro.data.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ir.yadavarchek.pro.MainActivity
import ir.yadavarchek.pro.R
import ir.yadavarchek.pro.YadavarChekApp.Companion.CHANNEL_REMINDER
import ir.yadavarchek.pro.domain.model.AppNotification
import ir.yadavarchek.pro.domain.model.NotificationType
import ir.yadavarchek.pro.domain.repository.CheckRepository
import ir.yadavarchek.pro.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.firstOrNull

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkRepository: CheckRepository,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val checkId = inputData.getLong(KEY_CHECK_ID, -1L)
        val daysLeft = inputData.getInt(KEY_DAYS_LEFT, 0)
        if (checkId == -1L) return Result.failure()

        val check = checkRepository.getCheckById(checkId) ?: return Result.failure()

        val title = "یادآور چک"
        val message = when {
            daysLeft == 0 -> "امروز سررسید چک ${check.checkNumber} از ${check.issuer} است"
            daysLeft == 1 -> "فردا سررسید چک ${check.checkNumber} از ${check.issuer} است"
            else -> "${daysLeft} روز دیگر سررسید چک ${check.checkNumber} از ${check.issuer} است"
        }

        showNotification(checkId.toInt(), title, message)

        notificationRepository.insertNotification(
            AppNotification(
                title = title,
                message = message,
                checkId = checkId,
                type = NotificationType.REMINDER
            )
        )
        return Result.success()
    }

    private fun showNotification(id: Int, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_REMINDER)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
    }

    companion object {
        const val KEY_CHECK_ID  = "check_id"
        const val KEY_DAYS_LEFT = "days_left"
    }
}
