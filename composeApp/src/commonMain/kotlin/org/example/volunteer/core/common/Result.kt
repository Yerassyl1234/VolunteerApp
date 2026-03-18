package org.example.volunteer.core.common

sealed class Result<out T> {
    data class Success<T> (val data:T) : Result<T>()
    data class Error(val exception: AppException): Result<Nothing>()
}