package com.yadavarcheck.tisa.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yadavarcheck.tisa.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    prefs: PreferencesManager
) : ViewModel() {
    val isOnboarded = prefs.isOnboarded
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
