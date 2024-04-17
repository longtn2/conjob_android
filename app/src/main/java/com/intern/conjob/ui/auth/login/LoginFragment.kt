package com.intern.conjob.ui.auth.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.arch.util.isValidPassword
import com.intern.conjob.databinding.FragmentLoginBinding
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEditText()
    }

    private fun isButtonEnable() {
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
                Toast.makeText(activity as OnBoardingActivity, getString(R.string.toast_login), Toast.LENGTH_SHORT).show()
            }

            btnForgotPassword.setOnClickListener {
                Toast.makeText(activity as OnBoardingActivity, getString(R.string.toast_forgot_password), Toast.LENGTH_SHORT).show()
            }

            btnApple.setOnClickListener {
                Toast.makeText(activity as OnBoardingActivity, getString(R.string.toast_apple_login), Toast.LENGTH_SHORT).show()
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

    private fun initEditText() {
        binding.apply {
            edtEmail.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutEmail.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_email_null)
                    else getString(R.string.validate_email)
                txtInputLayoutEmail.isErrorEnabled = !it.toString().isValidEmail()

            }

            edtPassword.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutPassword.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_password_null)
                    else getString(R.string.validate_password_require)
                txtInputLayoutPassword.isErrorEnabled = !it.toString().isValidPassword()
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}