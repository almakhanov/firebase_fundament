package com.example.fundament.presentation.registration

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.User
import com.example.fundament.extensions.register
import com.example.fundament.extensions.registerWithGoogle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrationRepository(val auth: FirebaseAuth){

    suspend fun register(user: User): AsyncResult<User> {
        return auth.register(user)
    }

    suspend fun registerWithGoogle(user: User, account: GoogleSignInAccount): AsyncResult<FirebaseUser> {
        return auth.registerWithGoogle(user, account)
    }
}