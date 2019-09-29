package com.example.fundament

import android.app.Application
import com.example.fundament.entities.User
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.auth.FirebaseUser
import org.koin.android.ext.android.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    companion object {
        var firebaseUser: FirebaseUser? = null
        var user: User? = null
    }
}