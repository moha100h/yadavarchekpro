package com.yadavarcheck.tisa.ui.screens.notifications
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.text.font.FontWeight; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.domain.model.AppNotification
import com.yadavarcheck.tisa.ui.viewmodel.NotificationViewModel
import com.yadavarcheck.tisa.util.toJalaliDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBack: () -> Unit, onNavigateToCheck: (Long) -> Unit, viewModel: NotificationViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text("اعلان‌ها") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }, actions = { if (state.notifications.isNotEmpty()) IconButton(onClick = { viewModel.markAllAsRead() }) { Icon(Icons.Default.DoneAll, null) } }) }
    ) { padding ->
        if (state.notifications.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("🔔", style = MaterialTheme.typography.displayMedium); Spacer(Modifier.height(8.dp)); Text("اعلانی ندارید") } }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.notifications, key = { it.id }) { notif ->
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = if (notif.isRead) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primaryContainer), onClick = { viewModel.markAsRead(notif.id); notif.checkId?.let { onNavigateToCheck(it) } }) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(notif.title, style = MaterialTheme.typography.titleSmall, fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.Normal)
                                Spacer(Modifier.height(4.dp))
                                Text(notif.message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(Modifier.height(4.dp))
                                Text(notif.createdAt.toJalaliDisplay(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            if (!notif.isRead) Box(Modifier.size(8.dp).then(Modifier), contentAlignment = Alignment.Center) { Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.primary) { Box(Modifier.size(8.dp)) } }
                        }
                    }
                }
            }
        }
    }
}
