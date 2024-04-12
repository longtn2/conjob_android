package com.intern.conjob.ui.auth.register.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.arch.util.isValidPassword
import com.intern.conjob.databinding.FragmentRegister2Binding
import com.intern.conjob.ui.auth.register.RegisterViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class Register2Fragment : BaseFragment(R.layout.fragment_register_2) {
    private val binding by viewBinding(FragmentRegister2Binding::bind)
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEditText()
    }

    private fun isButtonEnable() {
        binding.apply {
            btnContinue.isEnabled =
                (!edtEmail.text.isNullOrEmpty() && !edtPassword.text.isNullOrEmpty())
        }
    }

    private fun initListener() {
        binding.apply {
            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }

            btnContinue.setOnClickListener {
                if (edtEmail.text.toString().isValidEmail() && edtPassword.text.toString().isValidPassword()) {
                    Toast.makeText(activity, "Register", Toast.LENGTH_SHORT).show()
                } else {
                    edtEmail.isSelected = true
                    txtInputLayoutEmail.error = getString(R.string.validate_email)
                }
            }

            imgBtnClose.setOnClickListener {
                controller.popBackStack(R.id.OnBoardingFragment, false)
            }
        }
    }

    private fun initEditText() {
        binding.apply {
            edtEmail.doAfterTextChanged {
                isButtonEnable()
                with(it.isNullOrEmpty()) {
                    txtInputLayoutEmail.error = getString(R.string.validate_email_null)
                    txtInputLayoutEmail.isErrorEnabled = this
                    edtEmail.isSelected = this
                }
            }

            edtPassword.doAfterTextChanged {
                isButtonEnable()
                with(it.isNullOrEmpty()) {
                    txtInputLayoutPassword.error = getString(R.string.validate_password_null)
                    txtInputLayoutPassword.isErrorEnabled = this
                    edtPassword.isSelected = this
                }
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

}