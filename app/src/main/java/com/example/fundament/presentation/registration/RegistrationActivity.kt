package com.example.fundament.presentation.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.fundament.R
import com.example.fundament.extensions.alert
import com.example.fundament.extensions.toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistrationViewModel

    /**
     * Нужно для регистрации через гугл аккаунт
     */
    private var gso: GoogleSignInOptions? = null

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

        setGoogleAuth() // Регистрация через гугл аккаунт
    }

    /**
     * Регистрация через гугл аккаунт
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
                viewModel.registerWithGoogle(it)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    toast(user.toString())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    toast(task.exception.toString())
                }
            }
    }

    companion object {
        const val RC_SIGN_IN_WITH_GOOGLE = 1321
        const val TAG = "super tag"
    }
}
