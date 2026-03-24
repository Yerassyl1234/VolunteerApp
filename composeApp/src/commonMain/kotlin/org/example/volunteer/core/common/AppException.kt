package org.example.volunteer.core.common


sealed class AppException(message: String) : Exception(message) {
    class ValidationException(msg: String) : AppException(msg)
    class NetworkException(msg: String) : AppException(msg)
    class ServerException(val code: Int, msg: String) : AppException(msg)
    class UnauthorizedException : AppException("Unauthorized")
    class NotFoundException : AppException("Not found")
}
