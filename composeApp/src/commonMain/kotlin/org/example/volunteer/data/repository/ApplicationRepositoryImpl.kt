package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.core.common.safeApiCall
import org.example.volunteer.data.remote.api.ApplicationApi
import org.example.volunteer.data.remote.dto.toDomain
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.domain.repository.ApplicationRepository

class ApplicationRepositoryImpl(
    private val api: ApplicationApi,
) : ApplicationRepository {

    override fun getApplications(eventId: String): Flow<List<EventApplication>> = flow {
        emit(api.getApplications(eventId).map { it.toDomain() })
    }

    override suspend fun apply(eventId: String): NetworkResult<EventApplication> = safeApiCall {
        api.apply(eventId).toDomain()
    }

    override suspend fun cancel(applicationId: String): NetworkResult<Unit> = safeApiCall {
        api.cancel(applicationId)
    }

    override suspend fun accept(applicationId: String): NetworkResult<Unit> = safeApiCall {
        api.accept(applicationId)
    }

    override suspend fun reject(applicationId: String): NetworkResult<Unit> = safeApiCall {
        api.reject(applicationId)
    }
}