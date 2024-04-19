package com.intern.conjob.ui.auth.register

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.RegisterUserRemoteDataSource
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.repository.RegisterRepository
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(
    private val registerRepository: RegisterRepository = RegisterRepository(RegisterUserRemoteDataSource.getInstance())):
    BaseViewModel() {

    fun register(registerUser: RegisterUser): Flow<FlowResult<BaseResponse>> {
        return registerRepository.register(registerUser).bindLoading(this)
    }
}