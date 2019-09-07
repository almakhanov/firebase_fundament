package com.example.fundament

import com.example.fundament.base.CoroutineProvider
import com.example.fundament.presentation.sample.SampleRepository
import com.example.fundament.presentation.sample.SampleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    factory {
        CoroutineProvider()
    }
    single {
        FirebaseDatabase.getInstance().reference
    }

    single{
        FirebaseAuth.getInstance()
    }

    factory {
        SampleRepository(get())
    }

    viewModel {
        SampleViewModel(get())
    }
}
