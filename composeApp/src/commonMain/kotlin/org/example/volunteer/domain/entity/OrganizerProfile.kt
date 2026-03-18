package org.example.volunteer.domain.entity

data class OrganizerProfile(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val email: String,
    val isVerified: Boolean,
    val eventsCreated: Int,
    val totalVolunteersAttended: Int
)
