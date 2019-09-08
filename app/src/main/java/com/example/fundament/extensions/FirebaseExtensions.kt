package com.example.fundament.extensions

import com.example.fundament.entities.AsyncResult
import com.example.fundament.entities.Table
import com.example.fundament.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend inline fun <reified T> DatabaseReference.postData(tableName: String, data: T): AsyncResult<T> {
    return withContext(Dispatchers.Default) {
        suspendCoroutine<AsyncResult<T>> { continuation ->
            this@postData.child(tableName).push().key?.let { this@postData.child(tableName).child(it).setValue(data) }
                ?.addOnSuccessListener {
                    continuation.resume(AsyncResult.Success(data))
                }?.addOnFailureListener {
                    continuation.resume(AsyncResult.Error(it.localizedMessage.orEmpty(), 0))
                }
        }
    }
}

suspend inline fun <reified T> DatabaseReference.getData(tableName: String): AsyncResult<List<T>> {
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

suspend inline fun FirebaseAuth.register(user: User): AsyncResult<User> {
    return withContext(Dispatchers.Default) {
        suspendCoroutine<AsyncResult<User>> { continuation ->
            this@register.createUserWithEmailAndPassword(user.username.orEmpty(), user.password.orEmpty())
                .addOnSuccessListener {
                    val id = it?.user?.uid.orEmpty()
                    FirebaseDatabase.getInstance().reference.child(Table.USER).child(id).setValue(user)
                    continuation.resume(AsyncResult.Success(user))
                }.addOnFailureListener {
                    continuation.resume(AsyncResult.Error(it.localizedMessage.orEmpty(), 0))
                }
        }
    }
}

suspend inline fun FirebaseAuth.login(username: String, password: String): AsyncResult<User>{
    return withContext(Dispatchers.Default) {
        suspendCoroutine<AsyncResult<User>> { continuation ->
            this@login.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    val id = it?.user?.uid.orEmpty()
                    FirebaseDatabase.getInstance().reference.child(Table.USER).child(id)
                        .addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                                continuation.resume(AsyncResult.Error("Пользователь не найден", 0))
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                continuation.resume(AsyncResult.Success(p0.getValue(User::class.java)))
                            }
                        })
                }.addOnFailureListener {
                    continuation.resume(AsyncResult.Error(it.localizedMessage.orEmpty(), 0))
                }
        }
    }
}

