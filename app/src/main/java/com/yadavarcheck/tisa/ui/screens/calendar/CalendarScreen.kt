package com.yadavarcheck.tisa.ui.screens.calendar
import androidx.compose.foundation.background; import androidx.compose.foundation.clickable; import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.*; import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.draw.clip; import androidx.compose.ui.text.font.FontWeight; import androidx.compose.ui.text.style.TextAlign; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.ui.components.CheckCard; import com.yadavarcheck.tisa.ui.viewmodel.CheckViewModel; import com.yadavarcheck.tisa.util.JalaliCalendar
import java.util.Calendar as JCal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(onBack: () -> Unit, onNavigateToDetail: (Long) -> Unit, viewModel: CheckViewModel = hiltViewModel()) {
    val state by viewModel.listState.collectAsState()
    var curYear  by remember { mutableIntStateOf(JalaliCalendar.now().year) }
    var curMonth by remember { mutableIntStateOf(JalaliCalendar.now().month) }
    var selDay   by remember { mutableStateOf<Int?>(JalaliCalendar.now().day) }
    val checksMap = remember(state.checks) { state.checks.groupBy { val j = JalaliCalendar.fromMillis(it.dueDate); Triple(j.year, j.month, j.day) } }
    val selChecks = remember(selDay, checksMap, curYear, curMonth) { selDay?.let { checksMap[Triple(curYear, curMonth, it)] } ?: emptyList() }
    Scaffold(topBar = { TopAppBar(title = { Text("تقویم سررسید") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (curMonth == 1) { curMonth = 12; curYear-- } else curMonth--; selDay = null }) { Icon(Icons.Default.ChevronRight, null) }
                            Text("${JalaliCalendar.persianMonths[curMonth-1]} $curYear", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { if (curMonth == 12) { curMonth = 1; curYear++ } else curMonth++; selDay = null }) { Icon(Icons.Default.ChevronLeft, null) }
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) { JalaliCalendar.persianDaysShort.forEach { d -> Text(d, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary) } }
                        Spacer(Modifier.height(4.dp))
                        val daysInMonth = JalaliCalendar.daysInMonth(curYear, curMonth)
                        val firstMs = JalaliCalendar.JalaliDate(curYear, curMonth, 1).toMillis()
                        val firstCal = JCal.getInstance().apply { timeInMillis = firstMs }
                        val firstDow = (firstCal.get(JCal.DAY_OF_WEEK) + 5) % 7
                        val rows = (firstDow + daysInMonth + 6) / 7
                        for (row in 0 until rows) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                for (col in 0 until 7) {
                                    val day = row * 7 + col - firstDow + 1
                                    val hasCheck = day in 1..daysInMonth && checksMap.containsKey(Triple(curYear, curMonth, day))
                                    val isSel = day == selDay; val isToday = JalaliCalendar.now().let { it.year == curYear && it.month == curMonth && it.day == day }
                                    Box(modifier = Modifier.weight(1f).aspectRatio(1f).padding(2.dp).clip(CircleShape).background(when { isSel -> MaterialTheme.colorScheme.primary; isToday -> MaterialTheme.colorScheme.primaryContainer; else -> androidx.compose.ui.graphics.Color.Transparent }).then(if (day in 1..daysInMonth) Modifier.clickable { selDay = day } else Modifier), contentAlignment = Alignment.Center) {
                                        if (day in 1..daysInMonth) Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(day.toString(), style = MaterialTheme.typography.bodySmall, color = when { isSel -> MaterialTheme.colorScheme.onPrimary; isToday -> MaterialTheme.colorScheme.primary; else -> MaterialTheme.colorScheme.onSurface }, fontWeight = if (isToday || isSel) FontWeight.Bold else FontWeight.Normal)
                                            if (hasCheck) Box(Modifier.size(4.dp).clip(CircleShape).background(if (isSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.error))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (selDay != null) {
                item { Text("سررسیدهای ${JalaliCalendar.persianMonths[curMonth-1]} $selDay", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) }
                if (selChecks.isEmpty()) { item { Text("چکی برای این روز ندارید", color = MaterialTheme.colorScheme.onSurfaceVariant) } }
                else items(selChecks) { check -> CheckCard(check = check, onClick = { onNavigateToDetail(check.id) }) }
            }
        }
    }
}
