package com.intern.conjob.arch.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import com.intern.conjob.arch.util.DebounceOnClickListener

fun View.onClick(interval: Long = 400L, listenerBlock: (View) -> Unit) =
    setOnClickListener(DebounceOnClickListener(interval, listenerBlock))

fun View.showToastOnClick(context: Context, text: String) = setOnClickListener {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
