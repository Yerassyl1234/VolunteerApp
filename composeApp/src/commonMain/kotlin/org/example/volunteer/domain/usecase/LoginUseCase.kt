package org.example.volunteer.domain.usecase

import org.example.volunteer.domain.repository.SettingsRepository
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.UserRole

class LoginUseCase(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<UserRole> {
        val result = userRepository.login(email, password)
        if (result is Result.Success) {
            settingsRepository.saveTokens(result.data.accessToken, result.data.refreshToken)
            settingsRepository.saveRole(result.data.role)
        }
        return result.map { it.role }
    }
}