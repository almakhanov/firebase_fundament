package com.example.fundament.presentation.auth

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.User
import com.example.fundament.extensions.login
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(val auth: FirebaseAuth){

    suspend fun login(username: String, password: String): AsyncResult<User> {
        return auth.login(username, password)
    }

}