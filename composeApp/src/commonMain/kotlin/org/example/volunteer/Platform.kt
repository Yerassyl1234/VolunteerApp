package org.example.volunteer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform