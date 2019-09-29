package com.example.fundament.presentation

import android.content.Intent
import android.os.Bundle
import android.os.SharedMemory
import androidx.appcompat.app.AppCompatActivity
import com.example.fundament.presentation.auth.AuthActivity
import com.example.fundament.presentation.file_upload.FileUploadActivity
import com.example.fundament.presentation.registration.RegistrationActivity
import com.example.fundament.presentation.sample.SampleActivity
import com.example.fundament.presentation.shared_pref.SharedPrefActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startActivity(Intent(this, SampleActivity::class.java))
//        startActivity(Intent(this, RegistrationActivity::class.java))
//        startActivity(Intent(this, AuthActivity::class.java))
//        startActivity(Intent(this, FileUploadActivity::class.java))
        startActivity(Intent(this, SharedPrefActivity::class.java))
        finish()
    }
}
