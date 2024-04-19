package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.ForgotPasswordRemoteDataSource
import com.intern.conjob.data.response.BaseResponse
import kotlinx.coroutines.flow.Flow

class ForgotPasswordRepository(private val forgotPasswordRemoteDataSource : ForgotPasswordRemoteDataSource): Repository() {
    fun forgotPassword(email: String): Flow<FlowResult<BaseResponse>> = safeFlow {
        forgotPasswordRemoteDataSource.forgotPassword(email)
    }
}