package com.yadavarcheck.tisa.ui.screens.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.domain.model.Check
import com.yadavarcheck.tisa.ui.components.CheckCard
import com.yadavarcheck.tisa.ui.components.formatAmount

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToChecks: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddCheck: () -> Unit,
    onNavigateToCheckDetail: (Long) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("یادآور چک", fontWeight = FontWeight.Bold) },
                actions = {
                    BadgedBox(badge = { if (state.unreadNotifCount > 0) Badge { Text(state.unreadNotifCount.toString()) } }) {
                        IconButton(onClick = onNavigateToNotifications) {
                            Icon(Icons.Default.Notifications, contentDescription = "اعلان‌ها")
                        }
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "تنظیمات")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToAddCheck,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("افزودن چک") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Summary Cards
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryCard(modifier = Modifier.weight(1f), title = "دریافتنی", amount = state.totalReceivable, color = MaterialTheme.colorScheme.primaryContainer, textColor = MaterialTheme.colorScheme.onPrimaryContainer)
                    SummaryCard(modifier = Modifier.weight(1f), title = "پرداختنی", amount = state.totalPayable, color = MaterialTheme.colorScheme.errorContainer, textColor = MaterialTheme.colorScheme.onErrorContainer)
                }
            }

            // Quick Actions
            item {
                QuickActionsRow(
                    onChecks   = onNavigateToChecks,
                    onCalendar = onNavigateToCalendar,
                    onReports  = onNavigateToReports
                )
            }

            // Overdue
            if (state.overdueChecks.isNotEmpty()) {
                item {
                    SectionHeader("⚠️ معوق ({state.overdueChecks.size})", color = MaterialTheme.colorScheme.error)
                }
                items(state.overdueChecks.take(3)) { check ->
                    CheckCard(check = check, onClick = { onNavigateToCheckDetail(check.id) })
                }
            }

            // Upcoming
            if (state.upcomingChecks.isNotEmpty()) {
                item { SectionHeader("🔔 سررسید نزدیک ({state.upcomingChecks.size})") }
                items(state.upcomingChecks.take(5)) { check ->
                    CheckCard(check = check, onClick = { onNavigateToCheckDetail(check.id) })
                }
            }

            if (state.upcomingChecks.isEmpty() && state.overdueChecks.isEmpty() && !state.isLoading) {
                item {
                    EmptyDashboard(onAddCheck = onNavigateToAddCheck)
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(modifier: Modifier, title: String, amount: Long, color: androidx.compose.ui.graphics.Color, textColor: androidx.compose.ui.graphics.Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = color)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = textColor)
            Spacer(Modifier.height(4.dp))
            Text(formatAmount(amount), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}

@Composable
private fun QuickActionsRow(onChecks: () -> Unit, onCalendar: () -> Unit, onReports: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        QuickActionButton(Modifier.weight(1f), "چک‌ها", Icons.Default.List, onChecks)
        QuickActionButton(Modifier.weight(1f), "تقویم", Icons.Default.CalendarMonth, onCalendar)
        QuickActionButton(Modifier.weight(1f), "گزارش", Icons.Default.BarChart, onReports)
    }
}

@Composable
private fun QuickActionButton(modifier: Modifier, label: String, icon: ImageVector, onClick: () -> Unit) {
    OutlinedCard(modifier = modifier.clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun SectionHeader(title: String, color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground) {
    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(vertical = 4.dp))
}

@Composable
private fun EmptyDashboard(onAddCheck: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("📋", style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(16.dp))
        Text("هیچ چکی ثبت نشده", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("برای افزودن اولین چک روی دکمه + بزنید", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onAddCheck) { Text("افزودن چک") }
    }
}
