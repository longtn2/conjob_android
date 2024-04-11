package com.intern.conjob.arch.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.intern.conjob.ui.base.BaseFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal fun AppCompatActivity.replaceFragment(
    @IdRes containerId: Int, fragment: BaseFragment,
    t: (transaction: FragmentTransaction) -> Unit = {},
    isAddBackStack: Boolean = false
) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        val transaction = supportFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.replace(containerId, fragment, fragment.javaClass.simpleName)
        if (isAddBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commitAllowingStateLoss()
    }
}

fun <T> AppCompatActivity.collectFlow(
    targetFlow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collectBlock: ((T) -> Unit)
) {
    lifecycleScope.launch {
        when (minActiveState) {
            Lifecycle.State.CREATED -> {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    targetFlow.flowWithLifecycle(lifecycle)
                        .collect {
                            collectBlock(it)
                        }
                }
            }
            Lifecycle.State.STARTED -> {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    targetFlow.flowWithLifecycle(lifecycle)
                        .collect {
                            collectBlock(it)
                        }
                }
            }
            Lifecycle.State.RESUMED -> {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    targetFlow.flowWithLifecycle(lifecycle)
                        .collect {
                            collectBlock(it)
                        }
                }
            }
            else -> throw IllegalArgumentException("Unsupported minActiveState: $minActiveState")
        }
    }
}
