package com.example.fundament

import com.example.fundament.base.CoroutineProvider
import com.example.fundament.presentation.auth.AuthRepository
import com.example.fundament.presentation.auth.AuthViewModel
import com.example.fundament.presentation.file_upload.FileUploadRepository
import com.example.fundament.presentation.file_upload.FileUploadViewModel
import com.example.fundament.presentation.registration.RegistrationRepository
import com.example.fundament.presentation.registration.RegistrationViewModel
import com.example.fundament.presentation.sample.SampleRepository
import com.example.fundament.presentation.sample.SampleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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

    single {
        FirebaseStorage.getInstance().reference
    }

    factory {
        SampleRepository(get())
    }

    factory {
        RegistrationRepository(get())
    }

    factory {
        AuthRepository(get())
    }

    factory {
        FileUploadRepository(get())
    }

    viewModel {
        SampleViewModel(get())
    }

    viewModel {
        RegistrationViewModel(get())
    }

    viewModel {
        AuthViewModel(get())
    }

    viewModel {
        FileUploadViewModel(get())
    }
}
