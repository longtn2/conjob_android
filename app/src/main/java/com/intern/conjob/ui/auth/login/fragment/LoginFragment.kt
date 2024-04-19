package com.intern.conjob.ui.auth.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.emitErrorModel
import androidx.lifecycle.lifecycleScope
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.onError
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.arch.util.isValidPassword
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.databinding.FragmentLoginBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.auth.login.LoginViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.flow.launchIn

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initViews()
    }

    private fun isEnableButton() {
        binding.apply {
            btnLogin.isEnabled =
                (edtEmail.text.toString().isValidEmail() &&
                 edtPassword.text.toString().isValidPassword())
        }
    }

    private fun initListener() {
        binding.apply {
            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }

            btnLogin.setOnClickListener {
                viewModel.login(
                    LoginUser(
                        edtPassword.text.toString(),
                        edtEmail.text.toString()
                    )
                ).onSuccess {
                    (activity as OnBoardingActivity).startActivity(Intent(context, MainActivity::class.java))
                    (activity as OnBoardingActivity).finish()
                }.onError(
                    commonAction = {
                        if (it.message.isNullOrEmpty()) {
                            viewModel.emitErrorModel(it)
                        } else {
                            tvLoginValidate.visibility = View.VISIBLE
                            tvLoginValidate.text = it.message
                        }
                    },
                    normalAction = {
                        if (it.message.isNullOrEmpty()) {
                            viewModel.emitErrorModel(it)
                        } else {
                            tvLoginValidate.visibility = View.VISIBLE
                            tvLoginValidate.text = it.message
                        }
                    }
                ).launchIn(lifecycleScope)
            }

            btnForgotPassword.setOnClickListener {
                controller.navigate(R.id.action_LoginFragment_to_forgotPasswordFragment)
            }

            btnGoogle.setOnClickListener {
                Toast.makeText(activity as OnBoardingActivity, getString(R.string.toast_google_login), Toast.LENGTH_SHORT).show()
            }

            btnFacebook.setOnClickListener {
                Toast.makeText(activity as OnBoardingActivity, getString(R.string.toast_facebook_login), Toast.LENGTH_SHORT).show()
            }

            btnRegister.setOnClickListener {
                controller.navigate(R.id.action_LoginFragment_to_RegisterFragment)
            }
        }
    }

    private fun initViews() {
        binding.apply {
            edtEmail.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutEmail.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_email_null)
                    else getString(R.string.validate_email)
                txtInputLayoutEmail.isErrorEnabled = !it.toString().isValidEmail()
            }

            edtPassword.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutPassword.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_password_null)
                    else getString(R.string.validate_password_require)
                txtInputLayoutPassword.isErrorEnabled = !it.toString().isValidPassword()
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}