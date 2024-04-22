package com.intern.conjob.arch.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPref {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun getToken(): String = sharedPreferences.getString(Constants.TOKEN_KEY, "") ?: ""

    fun getRefreshToken(): String = sharedPreferences.getString(Constants.REFRESH_TOKEN_KEY, "") ?: ""

    fun saveToken(value: String) = sharedPreferences.edit().putString(Constants.TOKEN_KEY, value).apply()

    fun saveRefreshToken(value: String) = sharedPreferences.edit().putString(Constants.REFRESH_TOKEN_KEY, value).apply()

    companion object {
        private var INSTANCE: SharedPref? = null
        fun getInstance(): SharedPref {
            if (INSTANCE == null) {
                INSTANCE = SharedPref()
            }
            return INSTANCE!!
        }
    }
}
