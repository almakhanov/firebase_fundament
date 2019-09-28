package com.example.fundament.presentation.file_upload

import android.net.Uri
import com.example.fundament.entities.AsyncResult
import com.example.fundament.extensions.uploadFile
import com.google.firebase.storage.StorageReference

class FileUploadRepository(private val storage: StorageReference){

    suspend fun uploadFile(uri: Uri): AsyncResult<Uri>{
        return storage.uploadFile(uri)
    }

}