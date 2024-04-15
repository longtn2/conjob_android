package com.intern.conjob.ui.auth.register.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.DATE_FORMAT
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.arch.util.isValidName
import com.intern.conjob.arch.util.isValidPassword
import com.intern.conjob.arch.util.isValidPhone
import com.intern.conjob.databinding.FragmentRegisterBinding
import com.intern.conjob.ui.auth.register.RegisterViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterFragment : BaseFragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModels<RegisterViewModel>()
    private val calendar: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEditText()
        initDatePicker()
    }

    private fun initListener() {
        binding.apply {
            btnContinue.setOnClickListener {

                Toast.makeText(activity, getString(R.string.button_register), Toast.LENGTH_SHORT).show()
            }

            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }
        }
    }

    private fun isButtonEnable() {
        binding.apply {
            btnContinue.isEnabled =
                (edtFirstName.text.toString().isValidName() &&
                 edtLastName.text.toString().isValidName() &&
                 edtPhone.text.toString().isValidPhone() &&
                 !edtBirthday.text.isNullOrEmpty() &&
                 edtEmail.text.toString().isValidEmail() &&
                 edtPassword.text.toString().isValidPassword())
        }
    }

    private fun initEditText() {
        binding.apply {
            edtFirstName.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutFirstName.error = if (it.isNullOrEmpty()) getString(R.string.validate_first_name_null)
                    else getString(R.string.validate_first_name)
                txtInputLayoutFirstName.isErrorEnabled = !it.toString().isValidName()
            }

            edtLastName.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutLastName.error = if (it.isNullOrEmpty()) getString(R.string.validate_last_name_null)
                    else getString(R.string.validate_last_name)
                txtInputLayoutLastName.isErrorEnabled = !it.toString().isValidName()
            }

            edtPhone.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutPhone.error = if (it.isNullOrEmpty()) getString(R.string.validate_phone_null)
                    else getString(R.string.validate_phone)
                txtInputLayoutPhone.isErrorEnabled = !it.toString().isValidPhone()
            }

            edtBirthday.doAfterTextChanged { isButtonEnable() }

            edtEmail.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutEmail.error = if (it.isNullOrEmpty()) getString(R.string.validate_email_null)
                    else getString(R.string.validate_email)
                txtInputLayoutEmail.isErrorEnabled = !it.toString().isValidEmail()
            }

            edtPassword.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutPassword.error = if (it.isNullOrEmpty()) getString(R.string.validate_password_null)
                    else getString(R.string.validate_password_require)
                txtInputLayoutPassword.isErrorEnabled = !it.toString().isValidPassword()
                tvPasswordReq.visibility = if (it.toString().isValidPassword()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initDatePicker() {
        binding.apply {
            val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day
                binding.edtBirthday.setText(
                    SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.time)
                )
            }
            val datePicker = DatePickerDialog(
                activity as OnBoardingActivity,
                date,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )

            edtBirthday.setOnClickListener { datePicker.show() }
            imgDatePicker.setOnClickListener { datePicker.show() }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}