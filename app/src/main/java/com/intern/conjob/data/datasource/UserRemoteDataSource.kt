package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.UploadFile
import com.intern.conjob.data.model.User
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.UserInfoResponse
import okhttp3.RequestBody

class UserRemoteDataSource(private val api: APIService) {
    companion object {
        private var instance: UserRemoteDataSource? = null

        fun getInstance(): UserRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = UserRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun getUserInfo(userId: Long): BaseDataResponse<UserInfoResponse> = apiCall {
        api.getUserInfo(userId)
    }

    suspend fun getProfile(): BaseDataResponse<UserInfoResponse> = apiCall {
        api.getProfile()
    }

    suspend fun updateProfile(user: User) = apiCall {
        api.updateProfile(user)
    }

    suspend fun uploadAvatar(url: String, image: RequestBody) = apiCall {
        api.uploadAvatar(url, image)
    }

    suspend fun uploadAvatarInfo(avatarFile: UploadFile) = apiCall {
        api.uploadAvatarInfo(avatarFile)
    }
}
