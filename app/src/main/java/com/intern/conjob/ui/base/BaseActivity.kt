package com.intern.conjob.ui.base

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewErrorFlow
import com.intern.conjob.arch.extensions.LoadingAware
import com.intern.conjob.arch.extensions.ViewErrorAware
import com.intern.conjob.arch.extensions.collectFlow
import com.intern.conjob.arch.extensions.hideKeyboard
import com.intern.conjob.arch.extensions.showErrorAlert
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.ui.widget.CustomProgressDialog
import com.intern.conjob.R
import com.intern.conjob.arch.util.ErrorMessage
import java.net.HttpURLConnection

/**
 *
 * @author vuongphan
 */
abstract class BaseActivity(@LayoutRes layout: Int) : AppCompatActivity(layout) {

    protected open val viewModel: ViewModel? = null

    private val progressDialog by lazy {
        CustomProgressDialog.newInstance()
    }

    internal fun handleCommonError(errorModel: ErrorModel) {
        //Handle Logic
        if (errorModel is ErrorModel.Http.ApiError) {
            // Handle Api error
            var errorMessage: String = errorModel.message ?: ErrorModel.LocalErrorException.UN_KNOW_EXCEPTION.message
            when (errorModel.code) {
                HttpURLConnection.HTTP_BAD_GATEWAY.toString() -> errorMessage = ErrorMessage.BAD_GATEWAY_502.message
                HttpURLConnection.HTTP_NOT_FOUND.toString() -> errorMessage = ErrorMessage.NOT_FOUND_404.message
                HttpURLConnection.HTTP_INTERNAL_ERROR.toString() -> errorMessage = ErrorMessage.SERVER_ERROR_500.message
            }
            showErrorAlert(
                message = errorMessage,
                buttonTitleRes = R.string.OK, onOkClicked = {
                }
            )
        } else if (errorModel is ErrorModel.LocalError) {
            showErrorAlert(
                message = (errorModel as? ErrorModel.LocalError)?.errorMessage.toString(),
                buttonTitleRes = R.string.OK, onOkClicked = {
                }
            )
        }
    }

    abstract fun initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                hideKeyboardWhenTapOutsideEditText(ev).run {
                    return if (this) {
                        this
                    } else {
                        super.dispatchTouchEvent(ev)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboardWhenTapOutsideEditText(ev: MotionEvent): Boolean {
        val view: View? = currentFocus
        if (view is EditText) {
            val rect = Rect()
            view.getGlobalVisibleRect(rect)

            val bound = IntArray(2)
            view.getLocationOnScreen(bound)
            val x = ev.rawX + view.getLeft() - bound[0]
            val y = ev.rawY + view.getTop() - bound[1]

            if (!rect.contains(ev.x.toInt(), ev.y.toInt())
                || x < view.getLeft() || x > view.getRight()
                || y < view.getTop() || y > view.getBottom()
            ) {
                hideKeyboard(this)
                view.clearFocus()
                return true
            }
        }
        return false
    }

    protected fun handleLoading(viewModel: ViewModel?) {
        viewModel?.let {
            if (it is ViewErrorAware) {
                collectFlow(it.viewErrorFlow) { errorModel ->
                    handleCommonError(errorModel)
                }
            }
            if (it is LoadingAware) {
                collectFlow(it.loadingFlow) { isShow ->
                    handleProgressDialogStatus(isShow)
                }
            }
        }
    }

    protected fun handleProgressDialogStatus(isShow: Boolean) {
        if (isShow) {
            progressDialog.show(
                supportFragmentManager,
                CustomProgressDialog::class.java.simpleName
            )
        } else {
            progressDialog.dismissAllowingStateLoss()
        }
    }
}
