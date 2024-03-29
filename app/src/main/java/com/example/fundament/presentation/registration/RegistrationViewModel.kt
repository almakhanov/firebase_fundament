package com.example.fundament.presentation.registration

import androidx.lifecycle.MutableLiveData
import com.example.fundament.base.BaseViewModel
import com.example.fundament.entities.User
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class RegistrationViewModel(val repository: RegistrationRepository) : BaseViewModel(){

    val registrationLiveData = MutableLiveData<String>()

    fun register(name: String, username: String, password: String) {
        makeRequest({ repository.register(User(name, username, password)) }) { res ->
            unwrap(res) {
                registrationLiveData.value = "Вы успешно зарегистрировались ${it.name}!"
            }
        }
    }

    fun registerWithGoogle(account: GoogleSignInAccount) {
        makeRequest({repository.registerWithGoogle(User(account.displayName, account.email, account.id), account)}){ res->
            unwrap(res){
                registrationLiveData.value = "Вы успешно зарегистрировались ${it.name}!"
            }
        }
    }

    fun registerWithFacebook(accessToken: AccessToken?) {
        makeRequest({repository.registerWithFacebook(accessToken)}){ res->
            unwrap(res){

            }
        }
    }
}