package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val authRemoteDataSource: AuthRemoteDataSource) : Repository() {
    fun login(loginUser: LoginUser): Flow<FlowResult<LoginResponse>> = safeFlow {
        authRemoteDataSource.login(loginUser)
    }

    fun forgotPassword(email: String): Flow<FlowResult<BaseResponse>> = safeFlow {
        authRemoteDataSource.forgotPassword(email)
    }
}
