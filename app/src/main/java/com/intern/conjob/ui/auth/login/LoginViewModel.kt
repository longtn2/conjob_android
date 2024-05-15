package com.intern.conjob.ui.auth.login

import androidx.lifecycle.bindLoading
import androidx.lifecycle.viewModelScope
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.datasource.UserLocalDataSource
import com.intern.conjob.data.datasource.UserRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.model.User
import com.intern.conjob.data.repository.AuthRepository
import com.intern.conjob.data.repository.LocalRepository
import com.intern.conjob.data.repository.UserRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.LoginResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

class LoginViewModel(
    private val loginRepository: AuthRepository = AuthRepository(AuthRemoteDataSource.getInstance()),
    private val userRepository: UserRepository = UserRepository(
        UserRemoteDataSource.getInstance(),
        UserLocalDataSource.getInstance()
    )
) : BaseViewModel() {

    fun login(loginUser: LoginUser): Flow<FlowResult<BaseDataResponse<LoginResponse>>> =
        loginRepository.login(loginUser).bindLoading(this).onSuccess {
            it.data?.let { data ->
                LocalRepository().saveToken(
                    data.token,
                    data.refreshToken
                )
                userRepository.saveUserToLocal(User(
                    firstName = data.firstName,
                    lastName = data.lastName,
                    dob = data.dob,
                    email = data.email,
                    avatar = data.avatar,
                    phoneNumber = data.phoneNumber,
                    address = data.address,
                    gender = data.gender
                )).onSuccess { id ->
                    LocalRepository().saveLocalUserId(id)
                }.launchIn(viewModelScope)
            }
        }
}
