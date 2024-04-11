package com.intern.conjob.ui

import com.intern.conjob.R
import com.intern.conjob.arch.extensions.replaceFragment
import com.intern.conjob.ui.base.BaseActivity
import com.intern.conjob.ui.coin.CoinFragment

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun initialize() {
        replaceFragment(R.id.nav_host_fragment, CoinFragment.newInstance())
    }
}
