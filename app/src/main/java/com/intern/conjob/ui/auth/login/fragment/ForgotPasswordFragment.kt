package com.intern.conjob.ui.auth.login.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.databinding.FragmentForgotPasswordBinding
import com.intern.conjob.ui.auth.login.LoginViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class ForgotPasswordFragment: BaseFragment(R.layout.fragment_forgot_password) {
    private val binding by viewBinding(FragmentForgotPasswordBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

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
                Toast.makeText(activity, getString(R.string.send_code), Toast.LENGTH_SHORT).show()
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