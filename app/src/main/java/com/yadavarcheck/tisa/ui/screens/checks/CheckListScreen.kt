package com.yadavarcheck.tisa.ui.screens.checks
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.domain.model.CheckStatus; import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.ui.components.CheckCard; import com.yadavarcheck.tisa.ui.components.StatusBadge
import com.yadavarcheck.tisa.ui.viewmodel.CheckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(onBack: () -> Unit, onNavigateToDetail: (Long) -> Unit, onNavigateToAddEdit: (Long) -> Unit, viewModel: CheckViewModel = hiltViewModel()) {
    val state by viewModel.listState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showFilterSheet by remember { mutableStateOf(false) }
    LaunchedEffect(state.snackbar) { state.snackbar?.let { snackbarHostState.showSnackbar(it); viewModel.clearSnackbar() } }
    Scaffold(
        topBar = { TopAppBar(title = { Text("چک‌ها") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }, actions = { IconButton(onClick = { showFilterSheet = true }) { Icon(Icons.Default.FilterList, null) }; IconButton(onClick = { onNavigateToAddEdit(0L) }) { Icon(Icons.Default.Add, null) } }) },
        floatingActionButton = { FloatingActionButton(onClick = { onNavigateToAddEdit(0L) }) { Icon(Icons.Default.Add, null) } },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(value = state.searchQuery, onValueChange = { viewModel.setSearchQuery(it) }, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), placeholder = { Text("جستجو...") }, leadingIcon = { Icon(Icons.Default.Search, null) }, trailingIcon = { if (state.searchQuery.isNotBlank()) IconButton(onClick = { viewModel.setSearchQuery("") }) { Icon(Icons.Default.Clear, null) } }, singleLine = true, shape = MaterialTheme.shapes.large)
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = state.filterType == null, onClick = { viewModel.setFilterType(null) }, label = { Text("همه") })
                FilterChip(selected = state.filterType == CheckType.RECEIVABLE, onClick = { viewModel.setFilterType(if (state.filterType == CheckType.RECEIVABLE) null else CheckType.RECEIVABLE) }, label = { Text("دریافتنی") })
                FilterChip(selected = state.filterType == CheckType.PAYABLE, onClick = { viewModel.setFilterType(if (state.filterType == CheckType.PAYABLE) null else CheckType.PAYABLE) }, label = { Text("پرداختنی") })
            }
            if (state.isLoading) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() } }
            else if (state.checks.isEmpty()) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("📋", style = MaterialTheme.typography.displayMedium); Spacer(Modifier.height(8.dp)); Text("چکی یافت نشد") } } }
            else { LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { items(state.checks, key = { it.id }) { check -> CheckCard(check = check, onClick = { onNavigateToDetail(check.id) }) }; item { Spacer(Modifier.height(80.dp)) } } }
        }
    }
    if (showFilterSheet) {
        ModalBottomSheet(onDismissRequest = { showFilterSheet = false }) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("فیلتر وضعیت", style = MaterialTheme.typography.titleMedium)
                CheckStatus.entries.forEach { status -> Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { RadioButton(selected = state.filterStatus == status, onClick = { viewModel.setFilterStatus(if (state.filterStatus == status) null else status); showFilterSheet = false }); StatusBadge(status) } }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
