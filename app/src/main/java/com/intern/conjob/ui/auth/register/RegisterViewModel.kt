package com.intern.conjob.ui.auth.register

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.repository.AuthRepository
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(
    private val authRepository: AuthRepository = AuthRepository(AuthRemoteDataSource.getInstance())):
    BaseViewModel() {

    fun register(registerUser: RegisterUser): Flow<FlowResult<BaseResponse>> {
        return authRepository.register(registerUser).bindLoading(this)
    }
}
