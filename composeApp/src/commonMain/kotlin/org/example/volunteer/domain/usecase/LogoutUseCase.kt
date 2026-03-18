package org.example.volunteer.domain.usecase

import org.example.volunteer.domain.repository.SettingsRepository

class LogoutUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke() = settingsRepository.clear()
}