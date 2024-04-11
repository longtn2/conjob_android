package com.intern.conjob.arch.extensions

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

internal fun Context.hideKeyboard(activity: Activity) {
    val focusedView = activity.currentFocus
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(focusedView?.windowToken, 0)
}

internal fun Context.convertDpToPx(dp: Int): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        resources.displayMetrics
    ).toInt()

internal fun Context.showErrorAlert(
    message: String,
    @StringRes buttonTitleRes: Int,
    onOkClicked: () -> Unit = {}
) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setCancelable(false)
    builder.setNegativeButton(getString(buttonTitleRes)) { _, _ ->
        onOkClicked.invoke()
    }
    builder.create().show()
}
