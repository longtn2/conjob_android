package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.LoginRemoteDataSource
import com.intern.conjob.data.model.LoginUser
import com.intern.conjob.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow

class LoginRepository(private val loginRemoteDataSource: LoginRemoteDataSource) : Repository() {
    fun login(loginUser: LoginUser): Flow<FlowResult<LoginResponse>> = safeFlow {
        loginRemoteDataSource.login(loginUser)
    }
}