package com.intern.conjob

import android.app.Application
import com.intern.conjob.arch.util.SharedPref
import com.intern.conjob.arch.util.VideoPlayer

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(applicationContext)
        VideoPlayer.initializePlayer(applicationContext)
    }
}
