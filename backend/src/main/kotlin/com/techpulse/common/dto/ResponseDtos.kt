package com.techpulse.common.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val code: Int = 200
) {
    companion object {
        fun <T> success(data: T): BaseResponse<T> =
            BaseResponse(success = true, data = data)

        fun error(message: String, code: Int = 400): BaseResponse<Nothing> =
            BaseResponse(success = false, error = message, code = code)
    }
}

data class PagedResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)
