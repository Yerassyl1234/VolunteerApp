package org.example.volunteer.core.common

import org.example.volunteer.core.ui.UiText

sealed class AppException(message: String) : Exception(message) {
    class ValidationException(msg: String) : AppException(msg)
    class NetworkException(msg: String) : AppException(msg)
    class ServerException(val code: Int, msg: String) : AppException(msg)
    class UnauthorizedException : AppException("Unauthorized")
    class NotFoundException : AppException("Not found")
}
