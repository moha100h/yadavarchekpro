package com.yadavarcheck.tisa.ui.screens.reports
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Modifier; import androidx.compose.ui.text.font.FontWeight; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.domain.model.CheckStatus; import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.ui.components.formatAmount; import com.yadavarcheck.tisa.ui.viewmodel.CheckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(onBack: () -> Unit, viewModel: CheckViewModel = hiltViewModel()) {
    val state by viewModel.listState.collectAsState()
    val checks = state.checks
    val totalRecv = checks.filter { it.type == CheckType.RECEIVABLE && it.status !in listOf(CheckStatus.CANCELED, CheckStatus.RETURNED) }.sumOf { it.amount }
    val totalPay  = checks.filter { it.type == CheckType.PAYABLE  && it.status !in listOf(CheckStatus.CANCELED, CheckStatus.RETURNED) }.sumOf { it.amount }
    val overdue   = checks.filter { it.status == CheckStatus.OVERDUE }
    val pending   = checks.filter { it.status == CheckStatus.PENDING }
    val paid      = checks.filter { it.status in listOf(CheckStatus.PAID, CheckStatus.DEPOSITED) }
    Scaffold(topBar = { TopAppBar(title = { Text("گزارش‌ها") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text("خلاصه مالی", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(modifier = androidx.compose.ui.Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) { Column(Modifier.padding(16.dp)) { Text("دریافتنی", style = MaterialTheme.typography.labelMedium); Spacer(Modifier.height(4.dp)); Text(formatAmount(totalRecv) + " ت", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) } }
                    Card(modifier = androidx.compose.ui.Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) { Column(Modifier.padding(16.dp)) { Text("پرداختنی", style = MaterialTheme.typography.labelMedium); Spacer(Modifier.height(4.dp)); Text(formatAmount(totalPay) + " ت", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) } }
                }
            }
            item { Text("آمار وضعیت", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("معوق"); Text("${overdue.size} چک — ${formatAmount(overdue.sumOf { it.amount })} ت", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) }
                        HorizontalDivider()
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("در انتظار"); Text("${pending.size} چک — ${formatAmount(pending.sumOf { it.amount })} ت", fontWeight = FontWeight.Bold) }
                        HorizontalDivider()
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("تسویه‌شده"); Text("${paid.size} چک — ${formatAmount(paid.sumOf { it.amount })} ت", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) }
                        HorizontalDivider()
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("کل چک‌ها"); Text("${checks.size} چک", fontWeight = FontWeight.Bold) }
                    }
                }
            }
        }
    }
}
