package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.util.SharedPref

class LocalRepository : Repository() {
    fun saveToken(token: String?, refreshToken: String?) {
        token?.let { SharedPref.getInstance().saveToken(it) }
        refreshToken?.let { SharedPref.getInstance().saveRefreshToken(it) }
    }
}
