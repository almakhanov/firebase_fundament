package com.example.fundament.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.fundament.R
import com.example.fundament.extensions.alert
import com.example.fundament.extensions.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AuthActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    /**
     * Нужно для авторизации через гугл аккаунт
     */
    private var gso: GoogleSignInOptions? = null

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

        /**
         * Показывает тост сообщение при успешной авторизации
         */
        viewModel.loginLiveData.observe(this, Observer {
            toast(it)
        })

        /**
         * для отоброжения ошибок
         */
        viewModel.messageLiveData.observe(this, Observer {
            alert(message = it)
        })

        setGoogleAuth() // Регистрация через гугл аккаунт
    }

    /**
     * Авторизация через гугл аккаунт
     */
    private fun setGoogleAuth(){
        googleBtn.setOnClickListener {
            val signInIntent = GoogleSignIn.getClient(this, gso?:return@setOnClickListener).signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN_WITH_GOOGLE)
        }


        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(this.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.getResult(ApiException::class.java)?.let {
                viewModel.authWithGoogle(it)
            }
        }
    }

    companion object {
        const val RC_SIGN_IN_WITH_GOOGLE = 1321
        const val TAG = "super tag"
    }
}
