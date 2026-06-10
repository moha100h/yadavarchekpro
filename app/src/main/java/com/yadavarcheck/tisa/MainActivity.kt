package com.yadavarcheck.tisa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import com.yadavarcheck.tisa.ui.navigation.YadavarChekNavHost
import com.yadavarcheck.tisa.ui.theme.YadavarChekTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YadavarChekTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    YadavarChekNavHost()
                }
            }
        }
    }
}
