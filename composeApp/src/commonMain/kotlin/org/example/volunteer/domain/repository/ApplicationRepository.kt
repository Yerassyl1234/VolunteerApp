package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.core.common.Result

interface ApplicationRepository {
    fun getApplications(eventId: String): Flow<List<EventApplication>>
    suspend fun apply(eventId: String): Result<EventApplication>
    suspend fun cancel(applicationId: String): Result<Unit>
    suspend fun accept(applicationId: String): Result<Unit>
    suspend fun reject(applicationId: String): Result<Unit>
}