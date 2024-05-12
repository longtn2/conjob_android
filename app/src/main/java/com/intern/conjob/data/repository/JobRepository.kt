package com.intern.conjob.data.repository

import com.intern.conjob.arch.data.Repository
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.safeFlow
import com.intern.conjob.data.datasource.JobRemoteDataSource
import com.intern.conjob.data.model.CreateJob
import com.intern.conjob.data.response.BaseResponse
import kotlinx.coroutines.flow.Flow

class JobRepository(private val jobRemoteDataSource: JobRemoteDataSource) : Repository() {

    fun createJob(createJob: CreateJob): Flow<FlowResult<BaseResponse>> = safeFlow {
        jobRemoteDataSource.createJob(createJob)
    }

}
