package com.intern.conjob.arch.extensions

import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.data.response.BaseResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Response<T>.exceptionOnSuccessResponse(): ErrorModel.Http? {
    if (isSuccessful) {
        this.body()?.let { successResponse ->
            if (successResponse is BaseResponse) {
                return ErrorModel.Http.ApiError(
                    code = successResponse.message,
                    message = successResponse.message,
                    apiUrl = this.raw().request.url.toString()
                )
            }
        }
    }
    return null
}

private val jsonParser = Json {
    ignoreUnknownKeys = true
}

@OptIn(ExperimentalSerializationApi::class)
fun <T> Response<T>.toError(): ErrorModel.Http {
    return try {
        exceptionOnSuccessResponse() ?: ErrorModel.Http.ApiError(
            code = code().toString(),
            message = jsonParser.decodeFromString<BaseResponse>(errorBody()?.string() ?: "").message
                ?: ErrorModel.LocalErrorException.UN_KNOW_EXCEPTION.message,
            apiUrl = this.raw().request.url.toString()
        )
    } catch (ex: Exception) {
        ErrorModel.Http.ApiError(
            code = code().toString(),
            message = jsonParser.decodeFromString<BaseResponse>(errorBody()?.string() ?: "").message
                ?: ErrorModel.LocalErrorException.UN_KNOW_EXCEPTION.message,
            apiUrl = this.raw().request.url.toString()
        )
    }
}

fun Throwable.toError(): ErrorModel {
    return when (this) {
        is SocketTimeoutException -> ErrorModel.LocalError(
            ErrorModel.LocalErrorException.REQUEST_TIME_OUT_EXCEPTION.message,
            ErrorModel.LocalErrorException.REQUEST_TIME_OUT_EXCEPTION.code
        )

        is ConnectException, is UnknownHostException -> ErrorModel.LocalError(
            ErrorModel.LocalErrorException.NO_INTERNET_EXCEPTION.message,
            ErrorModel.LocalErrorException.NO_INTERNET_EXCEPTION.code
        )

        else -> ErrorModel.LocalError(this.message.toString(), "1014")
    }
}
