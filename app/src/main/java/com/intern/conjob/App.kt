package com.intern.conjob

import android.app.Application
import com.intern.conjob.arch.util.SharedPref

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(applicationContext)
    }
}
