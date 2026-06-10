package com.yadavarcheck.tisa.ui.screens.checks
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.rememberScrollState; import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.text.font.FontWeight; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.domain.model.CheckStatus
import com.yadavarcheck.tisa.ui.components.StatusBadge; import com.yadavarcheck.tisa.ui.components.formatAmount
import com.yadavarcheck.tisa.ui.viewmodel.CheckViewModel
import com.yadavarcheck.tisa.util.daysUntil; import com.yadavarcheck.tisa.util.toJalaliDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckDetailScreen(checkId: Long, onBack: () -> Unit, onEdit: (Long) -> Unit, viewModel: CheckViewModel = hiltViewModel()) {
    val formState by viewModel.formState.collectAsState()
    val listState by viewModel.listState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showStatusSheet  by remember { mutableStateOf(false) }
    LaunchedEffect(checkId) { viewModel.loadCheck(checkId) }
    LaunchedEffect(listState.snackbar) { listState.snackbar?.let { snackbarHostState.showSnackbar(it); viewModel.clearSnackbar() } }
    val check = formState.check
    Scaffold(
        topBar = { TopAppBar(title = { Text("جزئیات چک") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }, actions = { IconButton(onClick = { onEdit(checkId) }) { Icon(Icons.Default.Edit, null) }; IconButton(onClick = { showDeleteDialog = true }) { Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error) } }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (formState.isLoading) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() } }
        else {
            Column(modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { StatusBadge(check.status); Text(check.type.label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary) }
                        Spacer(Modifier.height(12.dp))
                        Text(formatAmount(check.amount) + " تومان", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        val days = check.dueDate.daysUntil()
                        Text(when { days < 0 -> "سررسید: ${check.dueDate.toJalaliDisplay()} (${-days} روز گذشته)"; days == 0 -> "سررسید: امروز"; else -> "سررسید: ${check.dueDate.toJalaliDisplay()} ($days روز دیگر)" }, style = MaterialTheme.typography.bodyMedium, color = when { days < 0 -> MaterialTheme.colorScheme.error; days <= 3 -> MaterialTheme.colorScheme.tertiary; else -> MaterialTheme.colorScheme.onSurface })
                    }
                }
                OutlinedButton(onClick = { showStatusSheet = true }, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.SwapHoriz, null); Spacer(Modifier.width(8.dp)); Text("تغییر وضعیت") }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        if (check.checkNumber.isNotBlank()) Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("شماره چک", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(check.checkNumber, fontWeight = FontWeight.Medium) }
                        if (check.issuer.isNotBlank()) Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("صادرکننده", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(check.issuer, fontWeight = FontWeight.Medium) }
                        if (check.receiver.isNotBlank()) Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("گیرنده", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(check.receiver, fontWeight = FontWeight.Medium) }
                        if (check.bankName.isNotBlank()) Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("بانک", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(check.bankName, fontWeight = FontWeight.Medium) }
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("تاریخ ثبت", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(check.registrationDate.toJalaliDisplay(), fontWeight = FontWeight.Medium) }
                    }
                }
                if (check.description.isNotBlank()) { Card(modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text("توضیحات", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary); Spacer(Modifier.height(4.dp)); Text(check.description) } } }
            }
        }
    }
    if (showDeleteDialog) { AlertDialog(onDismissRequest = { showDeleteDialog = false }, title = { Text("حذف چک") }, text = { Text("این عمل قابل بازگشت نیست.") }, confirmButton = { TextButton(onClick = { viewModel.deleteCheck(checkId); showDeleteDialog = false; onBack() }) { Text("حذف", color = MaterialTheme.colorScheme.error) } }, dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("انصراف") } }) }
    if (showStatusSheet) { ModalBottomSheet(onDismissRequest = { showStatusSheet = false }) { Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { Text("تغییر وضعیت", style = MaterialTheme.typography.titleMedium); CheckStatus.entries.forEach { s -> Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { RadioButton(selected = check.status == s, onClick = { viewModel.updateStatus(checkId, s); showStatusSheet = false }); StatusBadge(s) } }; Spacer(Modifier.height(32.dp)) } } }
}
