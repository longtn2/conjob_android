package com.intern.conjob.ui.base

import androidx.lifecycle.ViewModel
import com.intern.conjob.arch.extensions.LoadingAware
import com.intern.conjob.arch.extensions.ViewErrorAware

/**
 *
 * @author vuongphan
 */
open class BaseViewModel : ViewModel(), ViewErrorAware, LoadingAware
