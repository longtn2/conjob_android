package com.intern.conjob.ui.home.post.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.JobTypeEnum
import com.intern.conjob.arch.util.isValidQuantity
import com.intern.conjob.databinding.FragmentCreateJobBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateJobFragment : BaseFragment(R.layout.fragment_create_job) {
    private val binding by viewBinding(FragmentCreateJobBinding::bind)
    private val calendar: Calendar = Calendar.getInstance()

    override fun getViewModel(): BaseViewModel = BaseViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
        initListener()
        initViews()
    }

    private fun initDatePicker() {
        binding.apply {
            val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day
                binding.edtExpiredDay.setText(
                    SimpleDateFormat(
                        Constants.POST_VIEW_DATE_FORMAT, Locale.US
                    ).format(calendar.time)
                )
            }
            val datePicker = DatePickerDialog(
                activity as MainActivity,
                date,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            datePicker.datePicker.minDate = calendar.time.time
            edtExpiredDay.setOnClickListener { datePicker.show() }
            imgDatePicker.setOnClickListener { datePicker.show() }
        }
    }

    private fun initListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                controller.popBackStack()
            }
            tvCreateJob.setOnClickListener {
                controller.popBackStack()
            }
        }
    }

    private fun initViews() {
        binding.apply {
            spinnerJobType.adapter = ArrayAdapter(
                activity as MainActivity,
                android.R.layout.simple_dropdown_item_1line,
                listOf(
                    JobTypeEnum.UNSELECTED.jobType,
                    JobTypeEnum.REMOTE.jobType,
                    JobTypeEnum.FULL_TIME.jobType,
                    JobTypeEnum.PART_TIME.jobType,
                    JobTypeEnum.HYBRID.jobType,
                    JobTypeEnum.ONSITE.jobType,
                    JobTypeEnum.FREELANCE.jobType,
                )
            )

            spinnerJobType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    apdapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        tvJobTypeHint.visibility = View.VISIBLE
                        view?.visibility = View.GONE
                    } else {
                        tvJobTypeHint.visibility = View.GONE
                        view?.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(apdapterView: AdapterView<*>?) = Unit
            }

            spinnerJobType.prompt = getString(R.string.job_type_default)

            edtTitle.doAfterTextChanged {
                isEnableButton()
            }

            edtDescription.doAfterTextChanged {
                isEnableButton()
            }

            edtBudget.doAfterTextChanged {
                isEnableButton()
            }

            edtLocation.doAfterTextChanged {
                isEnableButton()
            }

            edtExpiredDay.doAfterTextChanged {
                isEnableButton()
            }

            edtQuantity.doAfterTextChanged {
                isEnableButton()
            }
        }
    }

    private fun isEnableButton() {
        binding.apply {
            tvCreateJob.isEnabled =
                (!edtTitle.text.isNullOrEmpty()
                    && !edtDescription.text.isNullOrEmpty()
                    && !edtBudget.text.isNullOrEmpty()
                    && spinnerJobType.selectedItemPosition != 0
                    && !edtLocation.text.isNullOrEmpty()
                    && !edtExpiredDay.text.isNullOrEmpty()
                    && edtQuantity.text.toString().isValidQuantity())
        }
    }

}
