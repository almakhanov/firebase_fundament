package com.example.fundament.entities

data class User(
    var name: String? = null,
    var username: String? = null,
    var password: String? = null
){
    constructor() : this(null, null, null)
}