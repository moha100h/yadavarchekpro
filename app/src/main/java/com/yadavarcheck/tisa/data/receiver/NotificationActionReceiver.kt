package com.yadavarcheck.tisa.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import com.yadavarcheck.tisa.domain.model.CheckStatus
import com.yadavarcheck.tisa.domain.repository.CheckRepository
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {
    @Inject lateinit var checkRepo: CheckRepository

    override fun onReceive(context: Context, intent: Intent) {
        val checkId = intent.getLongExtra("check_id", -1L)
        if (checkId == -1L) return
        val action = intent.action ?: return
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        scope.launch {
            when (action) {
                ACTION_MARK_PAID     -> checkRepo.updateCheckStatus(checkId, CheckStatus.PAID)
                ACTION_MARK_DEPOSITED-> checkRepo.updateCheckStatus(checkId, CheckStatus.DEPOSITED)
            }
            val nm = ContextCompat.getSystemService(context, NotificationManager::class.java)
            nm?.cancel(checkId.toInt())
        }
    }

    companion object {
        const val ACTION_MARK_PAID      = "com.yadavarcheck.tisa.MARK_PAID"
        const val ACTION_MARK_DEPOSITED = "com.yadavarcheck.tisa.MARK_DEPOSITED"
    }
}
