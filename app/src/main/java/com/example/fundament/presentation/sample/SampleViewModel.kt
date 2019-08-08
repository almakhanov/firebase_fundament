package com.example.fundament.presentation.sample

import androidx.lifecycle.MutableLiveData
import com.example.fundament.base.BaseViewModel
import com.example.fundament.entities.Sample

class SampleViewModel(private val repository: SampleRepository) : BaseViewModel() {

    val sampleListLiveData = MutableLiveData<ArrayList<Sample>>()

    fun postSample(sampleObject: Sample) {
        repository.postSample(sampleObject)
    }

    fun getSamples(){
        makeRequest({repository.getSamples()}){ res->
            unwrap(res){
                sampleListLiveData.value = it as ArrayList<Sample>
            }
        }
    }
}