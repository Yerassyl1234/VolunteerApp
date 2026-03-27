package org.example.volunteer.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.EventApplication

interface ApplicationRepository {
    fun getApplications(eventId: String): Flow<List<EventApplication>>
    suspend fun apply(eventId: String): NetworkResult<EventApplication>
    suspend fun cancel(applicationId: String): NetworkResult<Unit>
    suspend fun accept(applicationId: String): NetworkResult<Unit>
    suspend fun reject(applicationId: String): NetworkResult<Unit>
}