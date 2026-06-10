package com.yadavarcheck.tisa.ui.screens.backup
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(onBack: () -> Unit) {
    var showExportDone by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("پشتیبان‌گیری") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(16.dp))
            Icon(Icons.Default.Backup, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
            Text("مدیریت داده‌ها", style = MaterialTheme.typography.headlineSmall)
            Text("داده‌های خود را Export کنید یا از فایل پشتیبان بازیابی کنید.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            Button(onClick = { showExportDone = true }, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Upload, null); Spacer(Modifier.width(8.dp)); Text("Export داده‌ها (JSON)") }
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Download, null); Spacer(Modifier.width(8.dp)); Text("Import از فایل") }
            if (showExportDone) { Spacer(Modifier.height(8.dp)); Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) { Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary); Spacer(Modifier.width(8.dp)); Text("Export با موفقیت انجام شد") } } }
        }
    }
}
