package com.intern.conjob.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewErrorFlow
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.intern.conjob.arch.extensions.collectFlow
import com.intern.conjob.ui.widget.CustomProgressDialog

/**
 *
 * @author vuongphan
 */
abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

    private val progressDialog by lazy {
        CustomProgressDialog.newInstance()
    }

    protected val controller: NavController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel()?.run {
            collectFlow(viewErrorFlow) {
                (activity as? BaseActivity)?.handleCommonError(it)
            }

            collectFlow(loadingFlow) {
                handleProgressDialogStatus(it)
            }
        }
    }

    private fun handleProgressDialogStatus(isShow: Boolean) {
        if (isShow) {
            progressDialog.show(
                childFragmentManager,
                CustomProgressDialog::class.java.simpleName
            )
        } else {
            progressDialog.dismissAllowingStateLoss()
        }
    }

    abstract fun getViewModel(): BaseViewModel?
}
