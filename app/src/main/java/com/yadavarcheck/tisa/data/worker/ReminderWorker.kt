package com.yadavarcheck.tisa.data.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.yadavarcheck.tisa.MainActivity
import com.yadavarcheck.tisa.R
import com.yadavarcheck.tisa.YadavarChekApp
import com.yadavarcheck.tisa.data.receiver.NotificationActionReceiver
import com.yadavarcheck.tisa.domain.model.AppNotification
import com.yadavarcheck.tisa.domain.model.NotificationType
import com.yadavarcheck.tisa.domain.repository.NotificationRepository

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val notifRepo: NotificationRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val checkId  = inputData.getLong("check_id", -1L)
        val checkNum = inputData.getString("check_num") ?: ""
        val amount   = inputData.getLong("amount", 0L)
        val daysLeft = inputData.getInt("days_left", 0)
        val issuer   = inputData.getString("issuer") ?: ""
        if (checkId == -1L) return Result.failure()

        val title = when (daysLeft) {
            0    -> "⚠️ امروز سررسید چک"
            1    -> "فردا سررسید چک"
            else -> "$daysLeft روز تا سررسید چک"
        }
        val body = "چک شماره $checkNum — ${formatAmount(amount)} تومان — $issuer"

        notifRepo.insertNotification(AppNotification(checkId = checkId, title = title, message = body, type = NotificationType.REMINDER))
        showNotification(checkId, title, body)
        return Result.success()
    }

    private fun showNotification(checkId: Long, title: String, body: String) {
        val tapIntent = PendingIntent.getActivity(
            context, checkId.toInt(),
            Intent(context, MainActivity::class.java).apply { putExtra("check_id", checkId) },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val paidIntent = PendingIntent.getBroadcast(
            context, (checkId * 10 + 1).toInt(),
            Intent(context, NotificationActionReceiver::class.java).apply {
                action = NotificationActionReceiver.ACTION_MARK_PAID
                putExtra("check_id", checkId)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, YadavarChekApp.CHANNEL_REMINDER)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(tapIntent)
            .addAction(0, "پرداخت شد", paidIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        ContextCompat.getSystemService(context, NotificationManager::class.java)
            ?.notify(checkId.toInt(), notification)
    }

    private fun formatAmount(amount: Long): String {
        return "%,d".format(amount)
    }
}
