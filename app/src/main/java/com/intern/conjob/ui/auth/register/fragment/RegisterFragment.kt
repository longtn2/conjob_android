package com.intern.conjob.ui.auth.register.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.databinding.FragmentRegisterBinding
import com.intern.conjob.ui.auth.register.RegisterViewModel
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.onboarding.OnBoardingActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterFragment: BaseFragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModels<RegisterViewModel>()
    private val calendar: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEditText()
        initDatePicker()
        initDropdown()
    }

    private fun initListener() {
        binding.apply {
            btnContinue.setOnClickListener {
                controller.navigate(R.id.action_RegisterFragment_to_Register2Fragment)
            }

            imgBtnClose.setOnClickListener {
                controller.popBackStack()
            }
        }
    }

    private fun isButtonEnable() {
        binding.apply {
            btnContinue.isEnabled =
                (!edtFirstName.text.isNullOrEmpty() && !edtLastName.text.isNullOrEmpty())
        }
    }

    private fun initEditText() {
        binding.apply {
            edtFirstName.doAfterTextChanged {
                isButtonEnable()
                with(it.isNullOrEmpty()) {
                    txtInputLayoutFirstName.error = getString(R.string.validate_first_name_null)
                    txtInputLayoutFirstName.isErrorEnabled = this
                    edtFirstName.isSelected = this
                }
            }

            edtLastName.doAfterTextChanged {
                isButtonEnable()
                with(it.isNullOrEmpty()) {
                    txtInputLayoutLastName.error = getString(R.string.validate_last_name_null)
                    txtInputLayoutLastName.isErrorEnabled = this
                    edtLastName.isSelected = this
                }
            }
        }
    }

    private fun initDatePicker() {
        binding.apply {
            val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day
                binding.edtBirthday.setText(SimpleDateFormat("yyyy/MM/dd", Locale.US).format(calendar.time))
            }

            edtBirthday.setOnClickListener {
                DatePickerDialog(
                    activity as OnBoardingActivity,
                    date,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }
    }

    private fun initDropdown() {
        binding.spinnerGender.adapter = ArrayAdapter(
            activity as OnBoardingActivity,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Male", "Female", "Other")
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel
}