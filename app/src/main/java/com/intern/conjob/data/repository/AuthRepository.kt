package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.model.RegisterUser
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val authRemoteDataSource: AuthRemoteDataSource) : Repository() {
    fun login(loginUser: LoginUser): Flow<FlowResult<BaseDataResponse<LoginResponse>>> = safeFlow {
        authRemoteDataSource.login(loginUser)
    }

    fun forgotPassword(email: String): Flow<FlowResult<BaseResponse>> = safeFlow {
        authRemoteDataSource.forgotPassword(email)
    }

    fun register(registerUser: RegisterUser): Flow<FlowResult<BaseResponse>> = safeFlow {
        authRemoteDataSource.register(registerUser)
    }

    fun logout(token: Token): Flow<FlowResult<BaseResponse>> = safeFlow {
        authRemoteDataSource.logout(token)
    }
}
