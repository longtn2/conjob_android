package com.intern.conjob.ui.home.profile.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.FileUtils
import com.intern.conjob.arch.util.PermissionUtils
import com.intern.conjob.arch.util.isValidName
import com.intern.conjob.arch.util.isValidPhone
import com.intern.conjob.data.model.UploadFile
import com.intern.conjob.data.model.User
import com.intern.conjob.databinding.FragmentEditProfileBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.profile.EditProfileViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {
    private val binding by viewBinding(FragmentEditProfileBinding::bind)
    private val viewModel by viewModels<EditProfileViewModel>()
    private val args by navArgs<EditProfileFragmentArgs>()
    private val calendar: Calendar = Calendar.getInstance()
    private var imagePicker: ActivityResultLauncher<PickVisualMediaRequest>? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Glide.with(activity as MainActivity).load(uri).into(binding.imgAvatar)
                viewModel.avatar = uri
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
        initListener()
        initViews()

        viewModel.updateState.onEach {
            if (it == 2) {
                controller.previousBackStackEntry?.savedStateHandle?.set(
                    Constants.UPDATE_USER_KEY,
                    true
                )
                controller.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getGenderString(): String {
        return when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioBtnMale -> Constants.GENDER_MALE
            R.id.radioBtnFemale -> Constants.GENDER_FEMALE
            else -> Constants.GENDER_OTHER
        }
    }

    private fun initDatePicker() {
        binding.apply {
            val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day
                binding.edtBirthday.setText(
                    SimpleDateFormat(Constants.VIEW_DATE_FORMAT, Locale.US).format(calendar.time)
                )
            }
            val datePicker = DatePickerDialog(
                activity as MainActivity,
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

    private fun initListener() {
        binding.apply {
            btnConfirm.setOnClickListener {
                if (!viewModel.isLoading()) {
                    viewModel.initState()
                    uploadAvatar()
                    val user = User(
                        firstName = edtFirstName.text.toString(),
                        lastName = edtLastName.text.toString(),
                        dob = SimpleDateFormat(Constants.DATE_FORMAT, Locale.US).format(calendar.time),
                        address = edtAddress.text.toString(),
                        phoneNumber = edtPhone.text.toString(),
                        gender = getGenderString().lowercase(),
                        email = args.user.email,
                        avatar = args.user.avatar
                    )
                    if (user != args.user) {
                        viewModel.updateProfile(user).launchIn(lifecycleScope)
                    } else {
                        viewModel.updateState()
                    }
                }
            }

            toolBar.setNavigationOnClickListener {
                controller.popBackStack()
            }

            imgAvatar.setOnClickListener {
                imagePicker?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun initViews() {
        binding.apply {
            edtFirstName.setText(args.user.firstName)
            edtLastName.setText(args.user.lastName)
            edtPhone.setText(args.user.phoneNumber)
            edtAddress.setText(args.user.address)
            edtBirthday.setText(args.user.dob?.let { dob ->
                SimpleDateFormat(Constants.DATE_FORMAT, Locale.US).parse(dob)?.let { it1 ->
                    SimpleDateFormat(Constants.VIEW_DATE_FORMAT, Locale.US).format(it1)
                }
            } ?: "")
            if (!args.user.avatar.isNullOrEmpty()) {
                Glide.with(activity as MainActivity).load(args.user.avatar)
                    .override((activity as MainActivity).convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE))
                    .into(imgAvatar)
            }
            when (args.user.gender?.uppercase()) {
                Constants.GENDER_MALE.uppercase() -> radioGroup.check(R.id.radioBtnMale)
                Constants.GENDER_FEMALE.uppercase() -> radioGroup.check(R.id.radioBtnFemale)
                else -> radioGroup.check(R.id.radioBtnOther)
            }

            initValidate()
        }
    }

    private fun initValidate() {
        binding.apply {
            edtFirstName.doAfterTextChanged {
                txtInputLayoutFirstName.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_first_name_null)
                    else getString(R.string.validate_first_name)
                txtInputLayoutFirstName.isErrorEnabled = !it.toString().isValidName()
            }

            edtLastName.doAfterTextChanged {
                txtInputLayoutLastName.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_last_name_null)
                    else getString(R.string.validate_last_name)
                txtInputLayoutLastName.isErrorEnabled = !it.toString().isValidName()
            }

            edtPhone.doAfterTextChanged {
                txtInputLayoutPhone.error =
                    if (it.isNullOrEmpty()) getString(R.string.validate_phone_null)
                    else getString(R.string.validate_phone)
                txtInputLayoutPhone.isErrorEnabled = !it.toString().isValidPhone()
            }
        }
    }

    private fun uploadAvatar() {
        if (viewModel.avatar == null) {
            viewModel.updateState()
        } else {
            viewModel.avatar?.let {
                if (PermissionUtils.checkImagePermission(activity as MainActivity)) {
                    val file = File(FileUtils.getPath(it, activity as MainActivity) ?: "")
                    if (!file.path.isNullOrEmpty()) {
                        viewModel.uploadAvatarInfo(
                            UploadFile(
                                file.name,
                                file.extension
                            )
                        ).onSuccess { response ->
                            response.data?.url?.let { url ->
                                viewModel.uploadAvatar(url, file).launchIn(lifecycleScope)
                            }
                        }.launchIn(lifecycleScope)
                    }
                }
            }
        }
    }
}
