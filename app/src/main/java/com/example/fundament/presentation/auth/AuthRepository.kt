package com.example.fundament.presentation.auth

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.User
import com.example.fundament.extensions.login
import com.example.fundament.extensions.authWithGoogle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(val auth: FirebaseAuth){

    suspend fun login(username: String, password: String): AsyncResult<User> {
        return auth.login(username, password)
    }

    suspend fun authWithGoogle(user: User, account: GoogleSignInAccount): AsyncResult<User> {
        return auth.authWithGoogle(user, account)
    }
}