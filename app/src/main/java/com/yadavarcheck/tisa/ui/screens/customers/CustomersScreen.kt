package com.yadavarcheck.tisa.ui.screens.customers
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Alignment; import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.ui.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersScreen(onBack: () -> Unit, viewModel: CustomerViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newMobile by remember { mutableStateOf("") }
    Scaffold(
        topBar = { TopAppBar(title = { Text("مشتریان") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }, actions = { IconButton(onClick = { showAddDialog = true }) { Icon(Icons.Default.PersonAdd, null) } }) },
        floatingActionButton = { FloatingActionButton(onClick = { showAddDialog = true }) { Icon(Icons.Default.Add, null) } }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(value = state.searchQuery, onValueChange = { viewModel.search(it) }, modifier = Modifier.fillMaxWidth().padding(16.dp), placeholder = { Text("جستجو...") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true, shape = MaterialTheme.shapes.large)
            if (state.customers.isEmpty()) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("👤", style = MaterialTheme.typography.displayMedium); Spacer(Modifier.height(8.dp)); Text("مشتری‌ای ثبت نشده") } } }
            else { LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { items(state.customers, key = { it.id }) { c -> Card(Modifier.fillMaxWidth()) { ListItem(headlineContent = { Text(c.name) }, supportingContent = { if (c.mobile.isNotBlank()) Text(c.mobile) }, leadingContent = { Icon(Icons.Default.Person, null) }, trailingContent = { IconButton(onClick = { viewModel.delete(c.id) }) { Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error) } }) } } } }
        }
    }
    if (showAddDialog) { AlertDialog(onDismissRequest = { showAddDialog = false }, title = { Text("افزودن مشتری") }, text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { OutlinedTextField(value = newName, onValueChange = { newName = it }, label = { Text("نام *") }, modifier = Modifier.fillMaxWidth()); OutlinedTextField(value = newMobile, onValueChange = { newMobile = it }, label = { Text("موبایل") }, modifier = Modifier.fillMaxWidth()) } }, confirmButton = { TextButton(onClick = { if (newName.isNotBlank()) { viewModel.add(newName.trim(), newMobile.trim()); newName = ""; newMobile = ""; showAddDialog = false } }) { Text("افزودن") } }, dismissButton = { TextButton(onClick = { showAddDialog = false }) { Text("انصراف") } }) }
}
