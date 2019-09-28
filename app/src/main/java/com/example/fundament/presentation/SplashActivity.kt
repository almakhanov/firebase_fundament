package com.example.fundament.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fundament.presentation.auth.AuthActivity
import com.example.fundament.presentation.registration.RegistrationActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }
}
