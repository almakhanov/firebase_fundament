package com.example.fundament.presentation.sample

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.Sample
import com.example.fundament.entities.Table
import com.example.fundament.extensions.getData
import com.example.fundament.extensions.postData
import com.google.firebase.database.DatabaseReference

class SampleRepository(private val firebase: DatabaseReference) {

    fun postSample(sampleObject: Sample) {
        firebase.postData(Table.SAMPLE, sampleObject)
    }

    suspend fun getSamples(): AsyncResult<List<Sample>> {
        return firebase.getData(Table.SAMPLE)
    }
}


