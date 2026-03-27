package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.repository.ApplicationRepository

class RejectApplicantUseCase(
    private val repository: ApplicationRepository,
) {
    suspend operator fun invoke(applicationId: String): NetworkResult<Unit> =
        repository.reject(applicationId)
}