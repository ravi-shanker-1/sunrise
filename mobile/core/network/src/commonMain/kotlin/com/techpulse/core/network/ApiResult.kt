package com.techpulse.core.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int? = null, val message: String, val throwable: Throwable? = null) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()

    val isSuccess get() = this is Success
    val isError get() = this is Error
    val isLoading get() = this is Loading

    fun <R> map(transform: (T) -> R): ApiResult<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        is Loading -> Loading
    }

    fun getOrNull(): T? = (this as? Success)?.data
    fun getOrThrow(): T = (this as? Success)?.data ?: throw IllegalStateException("ApiResult is not Success")
}

suspend fun <T> safeApiCall(block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: Exception) {
        ApiResult.Error(message = e.message ?: "Unknown error", throwable = e)
    }
}
