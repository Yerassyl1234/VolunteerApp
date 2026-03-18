package org.example.volunteer.domain.entity


data class Location(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)