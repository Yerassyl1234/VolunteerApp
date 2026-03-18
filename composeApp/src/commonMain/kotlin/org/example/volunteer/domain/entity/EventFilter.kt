package org.example.volunteer.domain.entity

data class EventFilter(
    val category: Category? = null,
    val query: String = "",
)