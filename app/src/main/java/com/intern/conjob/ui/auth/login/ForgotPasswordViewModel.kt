package com.intern.conjob.ui.auth.login

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.repository.AuthRepository
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ForgotPasswordViewModel(private val authRepository: AuthRepository = AuthRepository(
    AuthRemoteDataSource.getInstance())
) : BaseViewModel() {
    fun forgotPassword(email: String): Flow<FlowResult<BaseResponse>> =
        authRepository.forgotPassword(email).bindLoading(this)
}
