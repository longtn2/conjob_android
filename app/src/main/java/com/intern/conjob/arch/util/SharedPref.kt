package com.intern.conjob.arch.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun getToken(): String? = sharedPreferences?.getString(Constants.TOKEN_KEY, "")

    fun getRefreshToken(): String? = sharedPreferences?.getString(Constants.REFRESH_TOKEN_KEY, "")

    fun saveToken(value: String) = sharedPreferences?.edit()?.putString(Constants.TOKEN_KEY, value)?.apply()

    fun saveRefreshToken(value: String) = sharedPreferences?.edit()?.putString(Constants.REFRESH_TOKEN_KEY, value)?.apply()
}
