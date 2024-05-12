package com.intern.conjob.ui.home.profile

import androidx.lifecycle.bindCommonError
import androidx.lifecycle.viewModelScope
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.util.SharedPref
import com.intern.conjob.data.datasource.UserLocalDataSource
import com.intern.conjob.data.datasource.UserRemoteDataSource
import com.intern.conjob.data.model.User
import com.intern.conjob.data.repository.UserRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.UserInfoResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

class ProfileViewModel(
    private val userRepository: UserRepository = UserRepository(
        UserRemoteDataSource.getInstance(),
        UserLocalDataSource.getInstance()
    )
): BaseViewModel() {
    private val _user: MutableStateFlow<User> = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    fun getUserProfile(): Flow<FlowResult<BaseDataResponse<UserInfoResponse>>> {
        return userRepository.getProfile().bindCommonError(this)
            .onSuccess {
                it.data?.let { data ->
                    _user.value = User(
                        firstName = data.firstName,
                        lastName = data.lastName,
                        gender = data.gender,
                        address = data.address,
                        phoneNumber = data.phoneNumber,
                        avatar = data.avatar,
                        email = data.email,
                        dob = data.dob
                    )
                    userRepository.saveUserToLocal(_user.value).onSuccess { id ->
                        SharedPref.saveUserId(id)
                    }.launchIn(viewModelScope)
                }
            }
    }

    fun getUserFromLocal(): Boolean {
        val userId = SharedPref.getUserId()
        if (userId != (-1).toLong()) {
            userRepository.getUserFromLocal(userId).onSuccess { data ->
                _user.value = data
            }.launchIn(viewModelScope)
            return true
        }
        return false
    }
}
