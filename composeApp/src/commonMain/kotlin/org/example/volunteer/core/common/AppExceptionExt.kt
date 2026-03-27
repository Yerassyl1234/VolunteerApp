package org.example.volunteer.core.common

import org.example.volunteer.core.ui.UiText
import volunteerapp.composeapp.generated.resources.Res
import volunteerapp.composeapp.generated.resources.error_network
import volunteerapp.composeapp.generated.resources.error_not_found
import volunteerapp.composeapp.generated.resources.error_unauthorized

fun AppException.toUiText(): UiText = when (this) {
    is AppException.NetworkException -> UiText.DynamicString(message ?: "Ошибка сети")
    is AppException.UnauthorizedException -> UiText.ResString(Res.string.error_unauthorized)
    is AppException.NotFoundException -> UiText.ResString(Res.string.error_not_found)
    is AppException.ServerException -> UiText.DynamicString("Ошибка сервера: $code")
    is AppException.ValidationException -> UiText.DynamicString(message ?: "Ошибка валидации")
}