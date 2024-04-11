package com.intern.conjob.arch.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlow(targetFlow: Flow<T>, collectBlock: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            targetFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { value ->
                    collectBlock(value)
                }
        }
    }
}
