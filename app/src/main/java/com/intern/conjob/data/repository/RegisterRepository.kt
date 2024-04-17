package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.RegisterUserRemoteDataSource
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.response.BaseResponse
import kotlinx.coroutines.flow.Flow

class RegisterRepository(private val registerUserRemoteDataSource: RegisterUserRemoteDataSource) : Repository() {
    fun register(registerUser: RegisterUser): Flow<FlowResult<BaseResponse>> = safeFlow {
        registerUserRemoteDataSource.register(registerUser)
    }

}