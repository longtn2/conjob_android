package com.intern.conjob.arch.extensions

import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.intern.conjob.arch.util.ActivityViewBindingDelegate
import com.intern.conjob.arch.util.FragmentViewBindingDelegate
import com.intern.conjob.ui.base.BaseActivity
import com.intern.conjob.ui.base.BaseFragment

fun <T : ViewBinding> BaseActivity.viewBinding(
    bindingInflater: (LayoutInflater) -> T,
    beforeSetContent: () -> Unit = {}
) = ActivityViewBindingDelegate(this, bindingInflater, beforeSetContent)

fun <T : ViewBinding> BaseFragment.viewBinding(
    viewBindingFactory: (View) -> T,
    disposeEvents: T.() -> Unit = {}
) = FragmentViewBindingDelegate(this, viewBindingFactory, disposeEvents)

internal fun ensureMainThread() {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        throw IllegalThreadStateException("View can be accessed only on the main thread.")
    }
}
