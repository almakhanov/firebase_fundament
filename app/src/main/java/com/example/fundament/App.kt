package com.example.fundament

import android.app.Application
import org.koin.android.ext.android.startKoin

open class App: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this,listOf(appModule))
    }
}