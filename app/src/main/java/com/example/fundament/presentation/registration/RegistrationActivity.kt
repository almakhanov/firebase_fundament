package com.example.fundament.presentation.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fundament.R
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegistrationActivity : AppCompatActivity() {

    lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        viewModel = getViewModel()

        authButton.setOnClickListener{
            viewModel.register(
                nameEditText.text.toString(),
                loginEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }
}
