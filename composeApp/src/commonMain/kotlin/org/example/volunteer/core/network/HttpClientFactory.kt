package org.example.volunteer.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.volunteer.data.remote.dto.RefreshRequestDto
import org.example.volunteer.data.remote.dto.TokenPairResponseDto
import org.example.volunteer.domain.repository.SettingsRepository
import io.ktor.client.plugins.websocket.WebSockets

fun createHttpClient(settings: SettingsRepository): HttpClient =
    HttpClient {
        defaultRequest {
            url("https://volunteer-app-1usm.onrender.com")
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(WebSockets)
        install(Auth) {
            bearer {
                loadTokens {
                    val access = settings.getAccessToken() ?: ""
                    val refresh = settings.getRefreshToken() ?: ""
                    BearerTokens(access, refresh)
                }

                refreshTokens {
                    val refreshToken = oldTokens?.refreshToken ?: return@refreshTokens null
                    try {
                        val response: TokenPairResponseDto = client.post("/auth/refresh") {
                            contentType(ContentType.Application.Json)
                            setBody(RefreshRequestDto(refreshToken))
                            markAsRefreshTokenRequest()
                        }.body()
                        settings.saveTokens(response.accessToken, response.refreshToken)
                        BearerTokens(response.accessToken, response.refreshToken)
                    } catch (_: Exception) {
                        settings.clear()
                        null
                    }
                }
            }
        }
    }