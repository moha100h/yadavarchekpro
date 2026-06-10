package com.yadavarcheck.tisa.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yadavarcheck.tisa.domain.model.Check
import com.yadavarcheck.tisa.domain.model.CheckStatus
import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CheckCard(check: Check, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusBadge(check.status)
                    Text(
                        text = if (check.type == CheckType.RECEIVABLE) "دریافتنی" else "پرداختنی",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (check.type == CheckType.RECEIVABLE) StatusPaid else StatusOverdue
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(formatAmount(check.amount) + " تومان", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(2.dp))
                Text(
                    text = if (check.type == CheckType.RECEIVABLE) "از: ${check.issuer}" else "به: ${check.receiver}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (check.checkNumber.isNotBlank()) {
                    Text("شماره: ${check.checkNumber}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(formatDate(check.dueDate), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium)
                Text("سررسید", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun StatusBadge(status: CheckStatus) {
    val (bg, fg) = when (status) {
        CheckStatus.PENDING   -> StatusPending.copy(alpha = 0.15f) to StatusPending
        CheckStatus.PAID      -> StatusPaid.copy(alpha = 0.15f) to StatusPaid
        CheckStatus.DEPOSITED -> StatusDeposited.copy(alpha = 0.15f) to StatusDeposited
        CheckStatus.RETURNED  -> StatusReturned.copy(alpha = 0.15f) to StatusReturned
        CheckStatus.OVERDUE   -> StatusOverdue.copy(alpha = 0.15f) to StatusOverdue
        CheckStatus.CANCELED  -> StatusCanceled.copy(alpha = 0.15f) to StatusCanceled
    }
    Surface(color = bg, shape = MaterialTheme.shapes.small) {
        Text(status.label, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = fg)
    }
}

fun formatAmount(amount: Long): String = "%,d".format(amount)

fun formatDate(timestamp: Long): String {
    if (timestamp == 0L) return "—"
    return SimpleDateFormat("yyyy/MM/dd", Locale("fa")).format(Date(timestamp))
}
