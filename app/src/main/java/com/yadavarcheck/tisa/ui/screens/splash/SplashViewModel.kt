package com.yadavarcheck.tisa.ui.screens.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yadavarcheck.tisa.data.preferences.PreferencesManager
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(prefs: PreferencesManager) : ViewModel() {
    val isOnboarded = prefs.isOnboarded
}
