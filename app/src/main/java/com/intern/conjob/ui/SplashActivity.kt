package com.intern.conjob.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import com.intern.conjob.R
import com.intern.conjob.arch.util.Constants.DELAY_MILLIS
import com.intern.conjob.ui.base.BaseActivity
import com.intern.conjob.ui.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override fun initialize() {
        //Delay 3s tạm thời, xóa sau khi viết API ở đây
        Handler().postDelayed(
            {
                this@SplashActivity.startActivity(
                    Intent(
                        this@SplashActivity, OnBoardingActivity::class.java
                    )
                )
                this@SplashActivity.finish()
            }, DELAY_MILLIS
        )
    }
}