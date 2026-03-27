package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.repository.SettingsRepository
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.domain.entity.UserRole

class LoginUseCase(
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): NetworkResult<UserRole> {
        return when (val result = userRepository.login(email, password)) {
            is NetworkResult.Success -> {
                settingsRepository.saveTokens(result.data.accessToken, result.data.refreshToken)
                settingsRepository.saveRole(result.data.role)
                NetworkResult.Success(result.data.role)
            }
            is NetworkResult.Error -> result
            is NetworkResult.Loading -> result
        }
    }
}