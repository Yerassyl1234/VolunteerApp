package org.example.volunteer.domain.entity

data class VolunteerProfile(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val email: String,
    val eventsAttended: Int,
    val hoursVolunteered: Int
)
