package com.intern.conjob.ui.home.profile

import android.net.Uri
import androidx.lifecycle.bindCommonError
import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.data.datasource.UserLocalDataSource
import com.intern.conjob.data.datasource.UserRemoteDataSource
import com.intern.conjob.data.model.UploadFile
import com.intern.conjob.data.model.User
import com.intern.conjob.data.repository.UserRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.UploadAvatarResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditProfileViewModel(
    private val userRepository: UserRepository = UserRepository(
        UserRemoteDataSource.getInstance(),
        UserLocalDataSource.getInstance()
    )
) : BaseViewModel() {
    var user: User = User()
    var avatar: Uri? = null
    private var _updateState: MutableStateFlow<Int> = MutableStateFlow(0)
    val updateState: StateFlow<Int> = _updateState
    private var isUpdateProfile = false
    private var isUpdateAvatar = false
    private var isUploadAvatar = false

    fun initState() {
        _updateState.value = 0
    }

    fun updateState() {
        _updateState.value++
    }

    fun isLoading(): Boolean {
        return !isUpdateProfile && !isUpdateAvatar && !isUploadAvatar
    }

    fun updateProfile(user: User): Flow<FlowResult<BaseResponse>> {
        return userRepository.updateProfile(user).onStart {
            isUpdateProfile = true
        }.onCompletion {
            isUpdateProfile = false
        }.bindLoading(this).onSuccess {
            _updateState.value++
        }
    }

    fun uploadAvatar(url: String, file: File): Flow<FlowResult<Unit>> {
        val image = file.asRequestBody(Constants.IMAGE.toMediaTypeOrNull())
        return userRepository.uploadAvatar(url, image).onStart {
            isUploadAvatar = true
        }.onCompletion {
            isUploadAvatar = false
        }.bindLoading(this).bindCommonError(this).onSuccess {
            _updateState.value++
        }
    }

    fun uploadAvatarInfo(avatarFile: UploadFile): Flow<FlowResult<BaseDataResponse<UploadAvatarResponse>>> {
        return userRepository.uploadAvatarInfo(avatarFile).onStart {
            isUpdateAvatar = true
        }.onCompletion {
            isUpdateAvatar = false
        }.bindLoading(this).bindCommonError(this)
    }
}
