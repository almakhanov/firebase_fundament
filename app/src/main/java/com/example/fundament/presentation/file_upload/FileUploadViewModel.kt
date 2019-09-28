package com.example.fundament.presentation.file_upload

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.fundament.base.BaseViewModel

class FileUploadViewModel(private val repository: FileUploadRepository): BaseViewModel(){

    val downloadUriLiveData = MutableLiveData<Uri>()

    fun uploadFile(uri: Uri) {
        makeRequest({repository.uploadFile(uri)}){ res->
            unwrap(res){
                downloadUriLiveData.value = it
            }
        }

    }

}