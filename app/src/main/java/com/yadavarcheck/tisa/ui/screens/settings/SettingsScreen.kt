package com.yadavarcheck.tisa.ui.screens.settings
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit, onNavigateToBackup: () -> Unit) {
    var darkMode by remember { mutableStateOf(false) }
    var dynamicColor by remember { mutableStateOf(true) }
    var reminderDays by remember { mutableIntStateOf(3) }
    Scaffold(topBar = { TopAppBar(title = { Text("تنظیمات") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Text("ظاهر", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary) }
            item { Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(4.dp)) {
                ListItem(headlineContent = { Text("حالت تاریک") }, trailingContent = { Switch(checked = darkMode, onCheckedChange = { darkMode = it }) }, leadingContent = { Icon(Icons.Default.DarkMode, null) })
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                ListItem(headlineContent = { Text("رنگ پویا") }, trailingContent = { Switch(checked = dynamicColor, onCheckedChange = { dynamicColor = it }) }, leadingContent = { Icon(Icons.Default.Palette, null) })
            } } }
            item { Spacer(Modifier.height(8.dp)); Text("یادآوری", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary) }
            item { Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
                Text("یادآوری $reminderDays روز قبل از سررسید", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Slider(value = reminderDays.toFloat(), onValueChange = { reminderDays = it.toInt() }, valueRange = 1f..14f, steps = 12)
            } } }
            item { Spacer(Modifier.height(8.dp)); Text("داده", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary) }
            item { Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(4.dp)) {
                ListItem(headlineContent = { Text("پشتیبان‌گیری و بازیابی") }, leadingContent = { Icon(Icons.Default.Backup, null) }, trailingContent = { Icon(Icons.Default.ChevronRight, null) }, modifier = Modifier.then(Modifier).also { }, supportingContent = { Text("Export/Import داده‌ها") })
            } } }
            item { OutlinedButton(onClick = onNavigateToBackup, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Backup, null); Spacer(Modifier.width(8.dp)); Text("مدیریت پشتیبان") } }
        }
    }
}
