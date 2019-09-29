package com.example.fundament.presentation.shared_pref

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fundament.R
import com.example.fundament.entities.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shared_pref.*
import org.koin.android.ext.android.inject

class SharedPrefActivity : AppCompatActivity() {

    /**
     * Экземпляр создается в файле AppModule.kt
     */
    private val pref: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_pref)

        /**
         * GET data from shared pref
         */
        val login = pref.getString("myLogin", "")

        if (!login.isNullOrEmpty()) {
            editText.setText(login)
        }

        /**
         * PUT data to shared pref
         */
        saveBtn.setOnClickListener {
            val editor = pref.edit()
            editor.putString("myLogin", editText.text.toString())
            editor.apply()
        }
    }

    private fun getUser(): User {
        val gson = Gson()
        val json = pref.getString("myUser", "")
        val obj = gson.fromJson<Any>(json, User::class.java) as User
        return obj
    }

    private fun storeUser(user: User) {
        val prefsEditor = pref.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        prefsEditor.putString("myUser", json)
        prefsEditor.apply()
    }

    private fun isAuthorized(): Boolean {
        return pref.getBoolean("isAuth", false)
    }

    private fun storeAuth(isAuth: Boolean) {
        val editor = pref.edit()
        editor.putBoolean("isAuth", isAuth)
        editor.apply()
    }
}
