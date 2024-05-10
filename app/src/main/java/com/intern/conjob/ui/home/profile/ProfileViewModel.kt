package com.intern.conjob.ui.home.profile

import androidx.lifecycle.bindCommonError
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.data.datasource.UserRemoteDataSource
import com.intern.conjob.data.repository.UserRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.UserInfoResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ProfileViewModel(
    private val userRepository: UserRepository = UserRepository(UserRemoteDataSource.getInstance())
): BaseViewModel() {
    fun getUserProfile(): Flow<FlowResult<BaseDataResponse<UserInfoResponse>>> {
        return userRepository.getProfile().bindCommonError(this)
    }

}
