package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.UserLocalDataSource
import com.intern.conjob.data.datasource.UserRemoteDataSource
import com.intern.conjob.data.model.User
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : Repository() {
    fun getUserInfo(userId: Long): Flow<FlowResult<BaseDataResponse<UserInfoResponse>>> = safeFlow {
        userRemoteDataSource.getUserInfo(userId)
    }

    fun getProfile(): Flow<FlowResult<BaseDataResponse<UserInfoResponse>>> = safeFlow {
        userRemoteDataSource.getProfile()
    }

    fun getUserFromLocal(userId: Long): Flow<FlowResult<User>> = safeFlow {
        userLocalDataSource.getUser(userId)
    }

    fun saveUserToLocal(user: User): Flow<FlowResult<Long>> = safeFlow {
        userLocalDataSource.insertUser(user)
    }
}
