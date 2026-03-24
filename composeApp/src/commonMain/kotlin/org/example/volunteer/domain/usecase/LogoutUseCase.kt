package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.AppException
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.repository.SettingsRepository

class LogoutUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(): NetworkResult<Unit> =
        try {
            settingsRepository.clear()
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            NetworkResult.Error(
                AppException.NetworkException(e.message ?: "Logout failed")
            )
        }
}