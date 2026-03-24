package org.example.volunteer.core.common

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

sealed class NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: AppException) : NetworkResult<Nothing>()

    val isLoading get() = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun exceptionOrNull(): AppException? = when (this) {
        is Error -> exception
        else -> null
    }

    fun <R> map(transform: (T) -> R): NetworkResult<R> = when (this) {
        is Loading -> Loading
        is Success -> Success(transform(data))
        is Error -> this
    }

    fun onSuccess(action: (T) -> Unit): NetworkResult<T> {
        if (this is Success) action(data)
        return this
    }

    fun onError(action: (AppException) -> Unit): NetworkResult<T> {
        if (this is Error) action(exception)
        return this
    }

    fun onLoading(action: () -> Unit): NetworkResult<T> {
        if (this is Loading) action()
        return this
    }
}

suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResult<T> =
    withContext(Dispatchers.IO) {
        try {
            NetworkResult.Success(block())
        } catch (e: ClientRequestException) {
            NetworkResult.Error(e.toAppException())
        } catch (e: ServerResponseException) {
            NetworkResult.Error(AppException.ServerException(e.response.status.value, e.message))
        } catch (e: Exception) {
            NetworkResult.Error(AppException.NetworkException(e.message ?: "No connection"))
        }
    }

fun <T> networkResultFlow(block: suspend () -> T): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    try {
        emit(NetworkResult.Success(block()))
    } catch (e: ClientRequestException) {
        emit(NetworkResult.Error(e.toAppException()))
    } catch (e: ServerResponseException) {
        emit(NetworkResult.Error(AppException.ServerException(e.response.status.value, e.message)))
    } catch (e: Exception) {
        emit(NetworkResult.Error(AppException.NetworkException(e.message ?: "No connection")))
    }
}

fun <T> networkResultFlowWithRetry(
    times: Int = 3,
    initialDelay: Long = 1_000L,
    factor: Double = 2.0,
    block: suspend () -> T,
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)

    var currentDelay = initialDelay
    var lastException: AppException? = null

    repeat(times) { attempt ->
        try {
            emit(NetworkResult.Success(block()))
            return@flow
        } catch (e: ClientRequestException) {
            val appException = e.toAppException()
            if (!appException.isRetryable()) {
                emit(NetworkResult.Error(appException))
                return@flow
            }
            lastException = appException
        } catch (e: ServerResponseException) {
            lastException = AppException.ServerException(e.response.status.value, e.message)
        } catch (e: Exception) {
            lastException = AppException.NetworkException(e.message ?: "No connection")
        }

        if (attempt < times - 1) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong()
        }
    }
    emit(NetworkResult.Error(lastException ?: AppException.NetworkException("Unknown error")))
}

fun <T> Flow<T>.asNetworkResult(): Flow<NetworkResult<T>> =
    map<T, NetworkResult<T>> { NetworkResult.Success(it) }
        .onStart { emit(NetworkResult.Loading) }
        .catch { e ->
            emit(
                NetworkResult.Error(
                    when (e) {
                        is AppException -> e
                        else -> AppException.NetworkException(e.message ?: "Unknown error")
                    }
                )
            )
        }

private fun ClientRequestException.toAppException(): AppException =
    when (response.status) {
        Unauthorized -> AppException.UnauthorizedException()
        NotFound -> AppException.NotFoundException()
        else -> AppException.ServerException(response.status.value, message)
    }

private fun AppException.isRetryable(): Boolean = when (this) {
    is AppException.NetworkException -> true
    is AppException.ServerException -> code in 500..599
    else -> false
}

inline fun <T> NetworkResult<T>.handle(
    onSuccess: (T) -> Unit,
    onError: (AppException) -> Unit,
) {
    when (this) {
        is NetworkResult.Success -> onSuccess(data)
        is NetworkResult.Error -> onError(exception)
        is NetworkResult.Loading -> {}
    }
}

suspend inline fun <T> NetworkResult<T>.handleSuspend(
    onSuccess: suspend (T) -> Unit,
    onError: (AppException) -> Unit,
) {
    when (this) {
        is NetworkResult.Success -> onSuccess(data)
        is NetworkResult.Error -> onError(exception)
        is NetworkResult.Loading -> {}
    }
}