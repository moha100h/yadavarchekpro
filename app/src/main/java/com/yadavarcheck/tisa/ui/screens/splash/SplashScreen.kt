package com.yadavarcheck.tisa.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import com.yadavarcheck.tisa.R

@Composable
fun SplashScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isOnboarded by viewModel.isOnboarded.collectAsState(initial = null)

    val scale = remember { Animatable(0.5f) }
    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
        delay(800)
        when (isOnboarded) {
            true  -> onNavigateToDashboard()
            false -> onNavigateToOnboarding()
            null  -> onNavigateToOnboarding()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.scale(scale.value)) {
            Text(
                text = "✓",
                fontSize = 72.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "مدیریت هوشمند چک",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}
