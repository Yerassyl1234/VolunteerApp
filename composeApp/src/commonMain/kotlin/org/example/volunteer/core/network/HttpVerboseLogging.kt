package org.example.volunteer.core.network

/** Реализация в platform-слое (например Android: debug = подробные логи). */
fun interface HttpVerboseLogging {
    fun isEnabled(): Boolean
}
