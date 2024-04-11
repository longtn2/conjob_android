package com.intern.conjob.arch.extensions

import android.view.View
import com.intern.conjob.arch.util.DebounceOnClickListener

fun View.onClick(interval: Long = 400L, listenerBlock: (View) -> Unit) =
    setOnClickListener(DebounceOnClickListener(interval, listenerBlock))
