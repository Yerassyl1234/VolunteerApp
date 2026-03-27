package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.repository.SettingsRepository
import org.example.volunteer.domain.repository.UserRepository

class RegisterUseCase(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        role: UserRole,
    ): NetworkResult<UserRole> {
        return when (val result = userRepository.register(name, email, password, role)) {
            is NetworkResult.Success -> {
                settingsRepository.saveTokens(result.data.accessToken, result.data.refreshToken)
                settingsRepository.saveRole(result.data.role)
                settingsRepository.saveUserId(result.data.userId)
                NetworkResult.Success(result.data.role)
            }
            is NetworkResult.Error -> result
            is NetworkResult.Loading -> result
        }
    }
}