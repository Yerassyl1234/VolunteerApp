package org.example.volunteer.core.common

sealed class Result<out T> {
    data class Success<T> (val data:T) : Result<T>()
    data class Error(val exception: AppException): Result<Nothing>()
}

fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error   -> this
}