package com.yadavarcheck.tisa.ui.screens.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

data class OnboardingPage(val emoji: String, val title: String, val description: String)

private val pages = listOf(
    OnboardingPage("📋", "مدیریت هوشمند چک", "تمام چک‌های دریافتنی و پرداختنی خود را در یک جا مدیریت کنید"),
    OnboardingPage("🔔", "یادآور هوشمند", "قبل از سررسید چک‌ها به موقع یادآوری دریافت کنید"),
    OnboardingPage("📊", "گزارش‌های مالی", "با نمودارها و گزارش‌های حرفه‌ای وضعیت مالی خود را رصد کنید"),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            OnboardingPage(pages[page])
        }

        // Indicators
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { i ->
                val selected = pagerState.currentPage == i
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (selected) 24.dp else 8.dp, 8.dp)
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        }

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pagerState.currentPage < pages.size - 1) {
                TextButton(onClick = { scope.launch { viewModel.setOnboarded(); onFinish() } }) {
                    Text("رد کردن")
                }
                Button(onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } }) {
                    Text("بعدی")
                }
            } else {
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { scope.launch { viewModel.setOnboarded(); onFinish() } },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("شروع کنید", fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun OnboardingPage(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(page.emoji, fontSize = 96.sp)
        Spacer(Modifier.height(32.dp))
        Text(page.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Text(page.description, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
