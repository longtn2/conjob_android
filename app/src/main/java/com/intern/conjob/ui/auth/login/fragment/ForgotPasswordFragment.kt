package com.intern.conjob.ui.auth.login.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.emitErrorModel
import androidx.lifecycle.lifecycleScope
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.onError
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.databinding.FragmentForgotPasswordBinding
import com.intern.conjob.ui.auth.login.ForgotPasswordViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn

class ForgotPasswordFragment: BaseFragment(R.layout.fragment_forgot_password) {
    private val binding by viewBinding(FragmentForgotPasswordBinding::bind)
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initViews()
    }

    private fun initListener() {
        binding.apply {
            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }

            btnForgotPassword.setOnClickListener {
                viewModel.forgotPassword(edtEmail.text.toString())
                    .onSuccess {
                        controller.popBackStack()
                    }.onError(
                        normalAction = {
                            txtInputLayoutEmail.error = it.message
                        },
                        commonAction = {
                            viewModel.emitErrorModel(it)
                        }
                    ).launchIn(lifecycleScope)
            }
        }
    }

    private fun initViews() {
        binding.apply {
            edtEmail.doAfterTextChanged {
                with(it.toString().isValidEmail()) {
                    btnForgotPassword.isEnabled = this
                    txtInputLayoutEmail.error =
                        if (it.isNullOrEmpty()) getString(R.string.validate_email_null)
                        else getString(R.string.validate_email)
                    txtInputLayoutEmail.isErrorEnabled = !this
                }
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}