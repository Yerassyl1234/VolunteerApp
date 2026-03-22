package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.repository.ApplicationRepository

class RejectApplicantUseCase(
    private val repository: ApplicationRepository,
) {
    suspend operator fun invoke(applicationId: String): Result<Unit> =
        repository.reject(applicationId)
}