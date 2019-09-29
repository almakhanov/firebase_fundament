package com.example.fundament.presentation.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.fundament.R
import com.example.fundament.extensions.alert
import com.example.fundament.extensions.toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistrationViewModel

    /**
     * Нужно для регистрации через гугл аккаунт
     */
    private var gso: GoogleSignInOptions? = null

    /**
     * Нужно для регистрации через facebook аккаунт
     */
    private val callbackManager = CallbackManager.Factory.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        viewModel = getViewModel()

        authButton.setOnClickListener {
            viewModel.register(
                nameEditText.text.toString(),
                loginEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        /**
         * Показывает тост сообщение при успешной регистрации
         */
        viewModel.registrationLiveData.observe(this, Observer {
            this.toast(it)
        })

        /**
         * для отоброжения ошибок
         */
        viewModel.messageLiveData.observe(this, Observer {
            this.alert(message = it)
        })

        setGoogleAuth() // Регистрация через google аккаунт
        setFacebookAuth() // Регистрация через facebook аккаунт
    }

    /**
     * Регистрация через google аккаунт
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

    /**
     * Регистрация через facebook аккаунт
     */
    private fun setFacebookAuth(){
        facebookBtn.setOnClickListener{
            buttonFacebookLogin.performClick()
        }
        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                viewModel.registerWithFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                alert(message = error.localizedMessage)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.getResult(ApiException::class.java)?.let {
                viewModel.registerWithGoogle(it)
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val RC_SIGN_IN_WITH_GOOGLE = 1321
        const val TAG = "super tag"
    }
}
