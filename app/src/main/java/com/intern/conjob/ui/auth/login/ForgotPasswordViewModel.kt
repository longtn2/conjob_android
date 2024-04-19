package com.intern.conjob.ui.auth.login

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.ForgotPasswordRemoteDataSource
import com.intern.conjob.data.repository.ForgotPasswordRepository
import com.intern.conjob.data.response.BaseResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ForgotPasswordViewModel(private val forgotPasswordRepository: ForgotPasswordRepository = ForgotPasswordRepository(
    ForgotPasswordRemoteDataSource.getInstance())
) : BaseViewModel() {
    fun forgotPassword(email: String): Flow<FlowResult<BaseResponse>> =
        forgotPasswordRepository.forgotPassword(email).bindLoading(this)
}