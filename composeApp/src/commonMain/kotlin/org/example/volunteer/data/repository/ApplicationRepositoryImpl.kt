package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.domain.repository.ApplicationRepository

class ApplicationRepositoryImpl : ApplicationRepository {
    override fun getApplications(eventId: String): Flow<List<EventApplication>> {
        TODO("Not yet implemented")
    }

    override suspend fun apply(eventId: String): NetworkResult<EventApplication> {
        TODO("Not yet implemented")
    }

    override suspend fun cancel(applicationId: String): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun accept(applicationId: String): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun reject(applicationId: String): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }
}