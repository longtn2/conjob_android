package com.intern.conjob.data.datasource

import com.intern.conjob.arch.extensions.apiCall
import com.intern.conjob.arch.modules.ApiClient
import com.intern.conjob.data.APIService
import com.intern.conjob.data.model.CreateJob
import com.intern.conjob.data.response.BaseResponse

class JobRemoteDataSource(private val api: APIService) {
    companion object {
        private var instance: JobRemoteDataSource? = null

        fun getInstance(): JobRemoteDataSource {
            if (instance == null) {
                val api: APIService = ApiClient.getInstance().apiService
                instance = JobRemoteDataSource(
                    api
                )
            }

            return instance!!
        }
    }

    suspend fun createJob(createJob: CreateJob): BaseResponse = apiCall {
        api.createJob(createJob)
    }

}
