package com.nutrimove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.navigation.AppNavHost
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Determine if onboarding is required
        val prefs = UserPreferences(this)
        val startOnboarding = runBlocking {
            prefs.onboardingCompletedFlow.first().not()
        }

        setContent {
            AppNavHost(startOnboarding = startOnboarding)
        }
    }
}
