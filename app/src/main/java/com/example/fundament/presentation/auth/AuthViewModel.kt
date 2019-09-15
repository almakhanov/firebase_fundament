package com.example.fundament.presentation.auth

import androidx.lifecycle.MutableLiveData
import com.example.fundament.base.BaseViewModel
import com.example.fundament.entities.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class AuthViewModel(val repository: AuthRepository): BaseViewModel(){

    val loginLiveData = MutableLiveData<String>()

    fun login(username: String, password: String) {
        makeRequest({repository.login(username, password)}){ res->
            unwrap(res){
                loginLiveData.value = "Вы успешно авторизовались ${it.name}!"
            }
        }
    }

    fun authWithGoogle(account: GoogleSignInAccount) {
        makeRequest({repository.authWithGoogle(User(account.displayName, account.email, account.id), account)}){ res->
            unwrap(res){
                loginLiveData.value = "Вы успешно авторизовались ${it.name}!"
            }
        }
    }
}