package com.example.fundament.presentation.registration

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.Sample
import com.example.fundament.entities.User
import com.example.fundament.extensions.register
import com.google.firebase.auth.FirebaseAuth

class RegistrationRepository(val auth: FirebaseAuth){

    suspend fun register(user: User): AsyncResult<User> {
        return auth.register(user)
    }
}