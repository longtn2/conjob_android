package com.intern.conjob.ui.auth.login.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.onError
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.databinding.FragmentForgotPasswordBinding
import com.intern.conjob.ui.auth.login.ForgotPasswordViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.flow.launchIn
import java.net.HttpURLConnection

class ForgotPasswordFragment : BaseFragment(R.layout.fragment_forgot_password) {
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
                            if ((it as? ErrorModel.Http.ApiError)?.code == HttpURLConnection.HTTP_BAD_REQUEST.toString()) {
                                txtInputLayoutEmail.error = it.message
                            } else {
                                (activity as OnBoardingActivity).handleCommonError(it)
                            }
                        },
                        commonAction = {
                            (activity as OnBoardingActivity).handleCommonError(it)
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
