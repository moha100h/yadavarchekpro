package com.yadavarcheck.tisa.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yadavarcheck.tisa.data.preferences.PreferencesManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {
    fun finishOnboarding() {
        viewModelScope.launch { prefs.setOnboarded(true) }
    }
}
