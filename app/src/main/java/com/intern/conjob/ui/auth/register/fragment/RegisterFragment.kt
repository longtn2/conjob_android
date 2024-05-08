package com.intern.conjob.ui.auth.register.fragment

import android.app.DatePickerDialog
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
import com.intern.conjob.arch.util.Constants.DATE_FORMAT
import com.intern.conjob.arch.util.Constants.GENDER_FEMALE
import com.intern.conjob.arch.util.Constants.GENDER_MALE
import com.intern.conjob.arch.util.Constants.GENDER_OTHER
import com.intern.conjob.arch.util.Constants.VIEW_DATE_FORMAT
import com.intern.conjob.arch.util.isValidEmail
import com.intern.conjob.arch.util.isValidName
import com.intern.conjob.arch.util.isValidPassword
import com.intern.conjob.arch.util.isValidPhone
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.databinding.FragmentRegisterBinding
import com.intern.conjob.ui.auth.register.RegisterViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.flow.launchIn
import java.net.HttpURLConnection
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
        initViews()
        initDatePicker()
    }

    private fun initListener() {
        binding.apply {
            btnContinue.setOnClickListener {
                viewModel.register(RegisterUser(
                    edtPassword.text.toString(),
                    edtFirstName.text.toString(),
                    edtLastName.text.toString(),
                    edtEmail.text.toString(),
                    edtPhone.text.toString(),
                    getGenderString(),
                    SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.time),
                    edtAddress.text.toString(),
                )).onSuccess {
                    Toast.makeText(activity, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    controller.navigate(R.id.action_RegisterFragment_to_LoginFragment)
                }.onError(
                    normalAction = {
                        if ((it as ErrorModel.Http.ApiError).code == HttpURLConnection.HTTP_BAD_REQUEST.toString()) {
                            txtInputLayoutEmail.error = it.message
                        } else {
                            viewModel.emitErrorModel(it)
                        }
                    },
                    commonAction = {
                        viewModel.emitErrorModel(it)
                    }
                ).launchIn(lifecycleScope)
            }

            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }
        }
    }

    private fun getGenderString(): String {
        return when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioBtnMale -> GENDER_MALE
            R.id.radioBtnFemale -> GENDER_FEMALE
            else -> GENDER_OTHER
        }
    }

    private fun isEnableButton() {
        binding.apply {
            btnContinue.isEnabled =
                (edtFirstName.text.toString().isValidName() &&
                 edtLastName.text.toString().isValidName() &&
                 edtPhone.text.toString().isValidPhone() &&
                 !edtBirthday.text.isNullOrEmpty() &&
                 !edtAddress.text.isNullOrEmpty() &&
                 edtEmail.text.toString().isValidEmail() &&
                 edtPassword.text.toString().isValidPassword())
        }
    }

    private fun initViews() {
        binding.apply {
            edtFirstName.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutFirstName.error = if (it.isNullOrEmpty()) getString(R.string.validate_first_name_null)
                    else getString(R.string.validate_first_name)
                txtInputLayoutFirstName.isErrorEnabled = !it.toString().isValidName()
            }

            edtLastName.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutLastName.error = if (it.isNullOrEmpty()) getString(R.string.validate_last_name_null)
                    else getString(R.string.validate_last_name)
                txtInputLayoutLastName.isErrorEnabled = !it.toString().isValidName()
            }

            edtPhone.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutPhone.error = if (it.isNullOrEmpty()) getString(R.string.validate_phone_null)
                    else getString(R.string.validate_phone)
                txtInputLayoutPhone.isErrorEnabled = !it.toString().isValidPhone()
            }

            edtBirthday.doAfterTextChanged { isEnableButton() }

            edtEmail.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutEmail.error = if (it.isNullOrEmpty()) getString(R.string.validate_email_null)
                    else getString(R.string.validate_email)
                txtInputLayoutEmail.isErrorEnabled = !it.toString().isValidEmail()
            }

            edtAddress.doAfterTextChanged {
                isEnableButton()
                with(it.isNullOrEmpty()) {
                    txtInputLayoutAddress.error = getString(R.string.validate_address_null)
                    txtInputLayoutAddress.isErrorEnabled = this
                }
            }

            edtPassword.doAfterTextChanged {
                isEnableButton()
                txtInputLayoutPassword.error = if (it.isNullOrEmpty()) getString(R.string.validate_password_null)
                    else getString(R.string.validate_password_require)
                txtInputLayoutPassword.isErrorEnabled = !it.toString().isValidPassword()
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
                    SimpleDateFormat(VIEW_DATE_FORMAT, Locale.US).format(calendar.time)
                )
            }
            val datePicker = DatePickerDialog(
                activity as OnBoardingActivity,
                date,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            datePicker.datePicker.maxDate = calendar.time.time
            edtBirthday.setOnClickListener { datePicker.show() }
            imgDatePicker.setOnClickListener { datePicker.show() }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
