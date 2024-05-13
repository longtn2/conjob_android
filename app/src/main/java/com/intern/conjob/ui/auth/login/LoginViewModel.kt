package com.intern.conjob.ui.auth.login

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.repository.AuthRepository
import com.intern.conjob.data.repository.LocalRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.LoginResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class LoginViewModel(
    private val loginRepository: AuthRepository = AuthRepository(AuthRemoteDataSource.getInstance()),
) : BaseViewModel() {

    fun login(loginUser: LoginUser): Flow<FlowResult<BaseDataResponse<LoginResponse>>> =
        loginRepository.login(loginUser).bindLoading(this).onSuccess {
            LocalRepository().saveToken(
                it.data?.token,
                it.data?.refreshToken
            )
        }
}
