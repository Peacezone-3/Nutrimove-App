package com.nutrimove

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the splash theme BEFORE super.onCreate
        setTheme(R.style.Theme_NutriMove_Splash)
        super.onCreate(savedInstanceState)
        // Immediately go to MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
