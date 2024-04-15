package com.intern.conjob.ui.auth.register.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.DATE_FORMAT
import com.intern.conjob.arch.util.Constants.GENDER_FEMALE
import com.intern.conjob.arch.util.Constants.GENDER_MALE
import com.intern.conjob.arch.util.Constants.GENDER_OTHER
import com.intern.conjob.arch.util.isValidName
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
        initGenderDropdown()
    }

    private fun initListener() {
        binding.apply {
            btnContinue.setOnClickListener {
                controller.navigate(R.id.action_RegisterFragment_to_Register2Fragment)
            }

            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }
        }
    }

    private fun isButtonEnable() {
        binding.apply {
            btnContinue.isEnabled =
                (edtFirstName.text.toString().isValidName() && edtLastName.text.toString()
                    .isValidName() && edtPhone.text.toString()
                    .isValidPhone() && !edtBirthday.text.isNullOrEmpty())
        }
    }

    private fun initEditText() {
        binding.apply {
            edtFirstName.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutFirstName.error =
                    if (it.isNullOrEmpty())
                        getString(R.string.validate_first_name_null)
                    else
                        getString(R.string.validate_first_name)

                txtInputLayoutFirstName.isErrorEnabled = !it.toString().isValidName()
            }

            edtLastName.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutLastName.error =
                    if (it.isNullOrEmpty())
                        getString(R.string.validate_last_name_null)
                    else
                        getString(R.string.validate_last_name)

                txtInputLayoutLastName.isErrorEnabled = !it.toString().isValidName()
            }

            edtPhone.doAfterTextChanged {
                isButtonEnable()
                txtInputLayoutPhone.error =
                    if (it.isNullOrEmpty())
                        getString(R.string.validate_phone_null)
                    else
                        getString(R.string.validate_phone)

                txtInputLayoutPhone.isErrorEnabled = !it.toString().isValidPhone()
            }

            edtBirthday.doAfterTextChanged {
                isButtonEnable()
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
                    SimpleDateFormat(DATE_FORMAT, Locale.US).format(
                        calendar.time
                    )
                )
            }
            val datePicker = DatePickerDialog(
                activity as OnBoardingActivity,
                date,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )

            edtBirthday.setOnClickListener {
                datePicker.show()
            }

            imgDatePicker.setOnClickListener {
                datePicker.show()
            }
        }
    }

    private fun initGenderDropdown() {
        binding.spinnerGender.adapter = ArrayAdapter(
            activity as OnBoardingActivity,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf(GENDER_MALE, GENDER_FEMALE, GENDER_OTHER)
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel
}