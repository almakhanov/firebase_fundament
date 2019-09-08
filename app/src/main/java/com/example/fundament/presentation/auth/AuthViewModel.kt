package com.example.fundament.presentation.auth

import androidx.lifecycle.MutableLiveData
import com.example.fundament.base.BaseViewModel

class AuthViewModel(val repository: AuthRepository): BaseViewModel(){

    val loginLiveData = MutableLiveData<String>()

    fun login(username: String, password: String) {
        makeRequest({repository.login(username, password)}){ res->
            unwrap(res){
                loginLiveData.value = "Вы успешно авторизовались"
            }
        }
    }
}