package com.example.fundament.presentation.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.fundament.R
import com.example.fundament.extensions.alert
import com.example.fundament.extensions.toast
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AuthActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = getViewModel()

        authButton.setOnClickListener{
            viewModel.login(
                loginEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        viewModel.loginLiveData.observe(this, Observer {
            toast(it)
        })

        viewModel.messageLiveData.observe(this, Observer {
            alert(message = it)
        })
    }
}
