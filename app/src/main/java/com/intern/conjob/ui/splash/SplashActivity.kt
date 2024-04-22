package com.intern.conjob.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import com.intern.conjob.R
import com.intern.conjob.arch.util.SharedPref
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseActivity
import com.intern.conjob.ui.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    override fun initialize() {
        if (SharedPref.getInstance().getToken().isNotEmpty()) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
        }
        finish()
    }
}
