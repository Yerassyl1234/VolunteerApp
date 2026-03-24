package org.example.volunteer.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.domain.repository.ApplicationRepository

class ApplicationRepositoryImpl : ApplicationRepository {
    override fun getApplications(eventId: String): Flow<List<EventApplication>> {
        TODO("Not yet implemented")
    }

    override suspend fun apply(eventId: String): Result<EventApplication> {
        TODO("Not yet implemented")
    }

    override suspend fun cancel(applicationId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun accept(applicationId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun reject(applicationId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}