package com.example.fundament.presentation.registration

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.User
import com.example.fundament.extensions.register
import com.example.fundament.extensions.authWithGoogle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class RegistrationRepository(val auth: FirebaseAuth){

    suspend fun register(user: User): AsyncResult<User> {
        return auth.register(user)
    }

    suspend fun registerWithGoogle(user: User, account: GoogleSignInAccount): AsyncResult<User> {
        return auth.authWithGoogle(user, account)
    }
}