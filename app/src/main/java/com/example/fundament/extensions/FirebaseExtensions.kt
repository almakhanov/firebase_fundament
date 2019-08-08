package com.example.fundament.extensions

import com.example.fundament.entities.AsyncResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun <T> DatabaseReference.postData(tableName: String, data: T) {
    this.child(tableName).push().key?.let {
        this.child(tableName).child(it).setValue(data)
    }
}

suspend inline fun <reified T> DatabaseReference.getData(tableName: String): AsyncResult<List<T>>{
    return withContext(Dispatchers.Default) {
        suspendCoroutine<AsyncResult<List<T>>> { continuation ->
            this@getData.child(tableName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(AsyncResult.Error(error.message, error.code))
                }

                override fun onDataChange(data: DataSnapshot) {
                    val list = arrayListOf<T>()
                    data.children.map {
                        list.add(it.getValue(T::class.java) as T)
                    }
                    continuation.resume(AsyncResult.Success(list))
                }
            })
        }
    }
}


