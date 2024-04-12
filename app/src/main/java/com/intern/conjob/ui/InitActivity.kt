package com.intern.conjob.ui

import android.content.Intent
import android.os.CountDownTimer
import com.intern.conjob.R
import com.intern.conjob.ui.base.BaseActivity
import com.intern.conjob.ui.onboarding.OnBoardingActivity

class InitActivity : BaseActivity(R.layout.activity_splash) {
    private val millisInFuture: Long = 3000
    private val countDownInterval: Long = 1000


    override fun initialize() {
        init()
    }

    private fun init() {
        (this@InitActivity).let {
            object : CountDownTimer(millisInFuture, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) = Unit

                override fun onFinish() {
                    it.startActivity(Intent(it, OnBoardingActivity::class.java))
                    it.finish()
                }

            }.start()
        }
    }
}