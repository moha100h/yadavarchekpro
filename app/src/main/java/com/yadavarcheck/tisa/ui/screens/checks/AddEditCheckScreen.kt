package com.yadavarcheck.tisa.ui.screens.checks
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.rememberScrollState; import androidx.compose.foundation.text.KeyboardOptions; import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons; import androidx.compose.material.icons.automirrored.filled.ArrowBack; import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Modifier; import androidx.compose.ui.text.input.KeyboardType; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.domain.model.Check; import com.yadavarcheck.tisa.domain.model.CheckStatus; import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.ui.viewmodel.CheckViewModel; import com.yadavarcheck.tisa.util.JalaliCalendar; import com.yadavarcheck.tisa.util.toJalaliShort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCheckScreen(checkId: Long, onBack: () -> Unit, viewModel: CheckViewModel = hiltViewModel()) {
    val formState by viewModel.formState.collectAsState()
    val isEdit = checkId > 0L
    LaunchedEffect(checkId) { viewModel.loadCheck(checkId) }
    LaunchedEffect(formState.isSaved) { if (formState.isSaved) { viewModel.resetForm(); onBack() } }
    val check = formState.check
    var checkNumber  by remember(check.id) { mutableStateOf(check.checkNumber) }
    var serialNumber by remember(check.id) { mutableStateOf(check.serialNumber) }
    var amount       by remember(check.id) { mutableStateOf(if (check.amount > 0) check.amount.toString() else "") }
    var issuer       by remember(check.id) { mutableStateOf(check.issuer) }
    var receiver     by remember(check.id) { mutableStateOf(check.receiver) }
    var bankName     by remember(check.id) { mutableStateOf(check.bankName) }
    var branchName   by remember(check.id) { mutableStateOf(check.branchName) }
    var description  by remember(check.id) { mutableStateOf(check.description) }
    var checkType    by remember(check.id) { mutableStateOf(check.type) }
    var checkStatus  by remember(check.id) { mutableStateOf(check.status) }
    var dueDate      by remember(check.id) { mutableStateOf(if (check.dueDate > 0) check.dueDate.toJalaliShort() else "") }
    var amountError  by remember { mutableStateOf(false) }
    var dueDateError by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text(if (isEdit) "ویرایش چک" else "افزودن چک") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }, actions = { TextButton(onClick = { amountError = amount.isBlank() || amount.toLongOrNull() == null; dueDateError = dueDate.isBlank(); if (amountError || dueDateError) return@TextButton; val dueMills = JalaliCalendar.parse(dueDate)?.toMillis() ?: 0L; viewModel.saveCheck(check.copy(checkNumber=checkNumber.trim(), serialNumber=serialNumber.trim(), amount=amount.toLongOrNull() ?: 0L, issuer=issuer.trim(), receiver=receiver.trim(), bankName=bankName.trim(), branchName=branchName.trim(), description=description.trim(), type=checkType, status=checkStatus, dueDate=dueMills)) }) { Text("ذخیره") } }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("نوع چک", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { CheckType.entries.forEach { type -> FilterChip(selected = checkType == type, onClick = { checkType = type }, label = { Text(type.label) }) } }
            OutlinedTextField(value = amount, onValueChange = { amount = it.filter { c -> c.isDigit() }; amountError = false }, label = { Text("مبلغ (تومان) *") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = amountError, supportingText = { if (amountError) Text("مبلغ الزامی است") }, leadingIcon = { Icon(Icons.Default.AttachMoney, null) })
            OutlinedTextField(value = dueDate, onValueChange = { dueDate = it; dueDateError = false }, label = { Text("تاریخ سررسید * (YYYY/MM/DD)") }, modifier = Modifier.fillMaxWidth(), isError = dueDateError, supportingText = { if (dueDateError) Text("تاریخ الزامی است") }, leadingIcon = { Icon(Icons.Default.CalendarMonth, null) })
            OutlinedTextField(value = checkNumber, onValueChange = { checkNumber = it }, label = { Text("شماره چک") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.Numbers, null) })
            OutlinedTextField(value = serialNumber, onValueChange = { serialNumber = it }, label = { Text("شماره سریال") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = issuer, onValueChange = { issuer = it }, label = { Text("صادرکننده") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.Person, null) })
            OutlinedTextField(value = receiver, onValueChange = { receiver = it }, label = { Text("گیرنده") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.PersonOutline, null) })
            OutlinedTextField(value = bankName, onValueChange = { bankName = it }, label = { Text("نام بانک") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.AccountBalance, null) })
            OutlinedTextField(value = branchName, onValueChange = { branchName = it }, label = { Text("شعبه") }, modifier = Modifier.fillMaxWidth())
            if (isEdit) { Text("وضعیت", style = MaterialTheme.typography.labelLarge); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { CheckStatus.entries.forEach { s -> FilterChip(selected = checkStatus == s, onClick = { checkStatus = s }, label = { Text(s.label) }) } } }
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("توضیحات") }, modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 5)
            Spacer(Modifier.height(16.dp))
        }
    }
}
