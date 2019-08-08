package com.example.fundament.base

interface BaseListenerAdapter<T> {
    fun onClick(pos: Int, item: T)
}