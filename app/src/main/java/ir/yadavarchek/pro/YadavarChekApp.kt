package ir.yadavarchek.pro

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class YadavarChekApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDER,
                "یادآور چک",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "یادآوری سررسید چک‌ها"
                enableVibration(true)
            }

            val overdueChannel = NotificationChannel(
                CHANNEL_OVERDUE,
                "چک‌های معوق",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "هشدار چک‌های معوق"
                enableVibration(true)
            }

            manager.createNotificationChannels(listOf(reminderChannel, overdueChannel))
        }
    }

    companion object {
        const val CHANNEL_REMINDER = "channel_reminder"
        const val CHANNEL_OVERDUE  = "channel_overdue"
    }
}
