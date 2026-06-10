package com.yadavarcheck.tisa.data.worker

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import com.yadavarcheck.tisa.domain.model.Check
import com.yadavarcheck.tisa.domain.repository.CheckRepository
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val checkRepo: CheckRepository
) {
    private val wm = WorkManager.getInstance(context)

    fun schedule(check: Check) {
        val now = System.currentTimeMillis()
        listOf(30, 15, 7, 3, 1, 0).forEach { days ->
            val fireAt = check.dueDate - days.toLong() * 86_400_000L
            if (fireAt > now) {
                val delay = fireAt - now
                val req = OneTimeWorkRequestBuilder<ReminderWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(workDataOf(
                        "check_id"   to check.id,
                        "check_num"  to check.checkNumber,
                        "amount"     to check.amount,
                        "days_left"  to days,
                        "issuer"     to check.issuer
                    ))
                    .addTag("reminder_${check.id}_${days}")
                    .build()
                wm.enqueueUniqueWork("reminder_${check.id}_${days}", ExistingWorkPolicy.REPLACE, req)
            }
        }
    }

    fun cancel(checkId: Long) {
        listOf(30, 15, 7, 3, 1, 0).forEach { days ->
            wm.cancelUniqueWork("reminder_${checkId}_${days}")
        }
    }

    fun rescheduleAll() {
        CoroutineScope(Dispatchers.IO).launch {
            checkRepo.getUpcomingChecks(60).collect { checks ->
                checks.forEach { schedule(it) }
            }
        }
    }
}
