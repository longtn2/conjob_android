package com.intern.conjob.ui.auth.login

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.repository.AuthRepository
import com.intern.conjob.data.response.LoginResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class LoginViewModel(private val authRepository: AuthRepository = AuthRepository(AuthRemoteDataSource.getInstance())
) : BaseViewModel() {

    fun login(loginUser: LoginUser): Flow<FlowResult<LoginResponse>> =
        authRepository.login(loginUser).bindLoading(this)
}
