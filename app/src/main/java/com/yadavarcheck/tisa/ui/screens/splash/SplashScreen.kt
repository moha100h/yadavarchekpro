package com.yadavarcheck.tisa.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.R

@Composable
fun SplashScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isOnboarded by viewModel.isOnboarded.collectAsState()

    LaunchedEffect(isOnboarded) {
        if (isOnboarded != null) {
            if (isOnboarded == true) onNavigateToDashboard()
            else onNavigateToOnboarding()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.app_name_full),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
