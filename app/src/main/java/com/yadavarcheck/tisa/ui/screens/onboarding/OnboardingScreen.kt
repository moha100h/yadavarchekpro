package com.yadavarcheck.tisa.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yadavarcheck.tisa.R

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages = listOf(
        Pair(R.string.onboarding_title_1, R.string.onboarding_desc_1),
        Pair(R.string.onboarding_title_2, R.string.onboarding_desc_2),
        Pair(R.string.onboarding_title_3, R.string.onboarding_desc_3)
    )
    var currentPage by remember { mutableIntStateOf(0) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(48.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(pages[currentPage].first),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(pages[currentPage].second),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pages.indices.forEach { i ->
                        Surface(
                            modifier = Modifier.size(if (i == currentPage) 24.dp else 8.dp, 8.dp),
                            shape = MaterialTheme.shapes.small,
                            color = if (i == currentPage) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.outlineVariant
                        ) {}
                    }
                }
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (currentPage < pages.lastIndex) currentPage++
                        else { viewModel.finishOnboarding(); onFinish() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (currentPage < pages.lastIndex) stringResource(R.string.next)
                        else stringResource(R.string.get_started)
                    )
                }
                if (currentPage < pages.lastIndex) {
                    TextButton(
                        onClick = { viewModel.finishOnboarding(); onFinish() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.skip))
                    }
                }
            }
        }
    }
}
