package com.intern.conjob.arch.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.intern.conjob.arch.util.Constants.USER_ID_KEY

object SharedPref {

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun getToken(): String? = sharedPreferences?.getString(Constants.TOKEN_KEY, "")

    fun getRefreshToken(): String? = sharedPreferences?.getString(Constants.REFRESH_TOKEN_KEY, "")

    fun saveToken(value: String) = sharedPreferences?.edit()?.putString(Constants.TOKEN_KEY, value)?.apply()

    fun saveRefreshToken(value: String) = sharedPreferences?.edit()?.putString(Constants.REFRESH_TOKEN_KEY, value)?.apply()

    fun getUserId(): Long = sharedPreferences?.getLong(USER_ID_KEY, -1) ?: -1

    fun saveUserId(value: Long) = sharedPreferences?.edit()?.putLong(USER_ID_KEY, value)?.apply()

}
