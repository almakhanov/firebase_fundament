package com.example.fundament.entities

sealed class AsyncResult<out T : Any?> {
    data class Success<out T : Any?>(val result: T? = null) : AsyncResult<T>()
    data class Error(val error: String, val code: Int = 0) : AsyncResult<Nothing>()
}