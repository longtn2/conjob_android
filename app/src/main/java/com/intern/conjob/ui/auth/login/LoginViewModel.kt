package com.intern.conjob.ui.auth.login

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.LoginRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.repository.LoginRepository
import com.intern.conjob.data.response.LoginResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class LoginViewModel(private val loginRepository: LoginRepository = LoginRepository(LoginRemoteDataSource.getInstance())
) : BaseViewModel() {

    fun login(loginUser: LoginUser): Flow<FlowResult<LoginResponse>> =
        loginRepository.login(loginUser).bindLoading(this)
}