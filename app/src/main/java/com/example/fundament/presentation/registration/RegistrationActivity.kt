package com.example.fundament.presentation.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.fundament.R
import com.example.fundament.extensions.alert
import com.example.fundament.extensions.toast
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

        viewModel.registrationLiveData.observe(this, Observer {
            this.toast(it)
        })

        viewModel.messageLiveData.observe(this, Observer {
            this.alert(message = it)
        })
    }
}
