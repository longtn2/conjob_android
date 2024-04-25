package com.intern.conjob.ui.widget

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

open class CustomClickableSpan(val color: Int): ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
        ds.isFakeBoldText = true
        ds.color = color
    }

    override fun onClick(widget: View) = Unit
}
