package org.example.volunteer.domain.entity

data class Badge(
    val id: String,
    val title: String,
    val iconUrl: String?,
    val isUnlocked: Boolean
)
