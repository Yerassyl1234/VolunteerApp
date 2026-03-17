package org.example.volunteer.core.domain.entity

data class Profile(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val email: String,
    val isVerified: Boolean,
)