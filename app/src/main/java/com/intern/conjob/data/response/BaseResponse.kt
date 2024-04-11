package com.intern.conjob.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @author vuong.phan
 */
@Serializable
data class BaseResponse(
    @SerialName("message") val message: String? = ""
)
