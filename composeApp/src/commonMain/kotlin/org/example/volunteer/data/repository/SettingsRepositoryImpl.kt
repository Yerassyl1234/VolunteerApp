package org.example.volunteer.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_ROLE = stringPreferencesKey("role")
    }

    override suspend fun getAccessToken(): String? =
        dataStore.data.map { it[KEY_ACCESS_TOKEN] }.first()

    override suspend fun getRefreshToken(): String? =
        dataStore.data.map { it[KEY_REFRESH_TOKEN] }.first()

    override suspend fun getRole(): Flow<UserRole?> =
        dataStore.data.map { prefs ->
            prefs[KEY_ROLE]?.let { UserRole.valueOf(it) }
        }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = accessToken
            prefs[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun saveRole(role: UserRole) {
        dataStore.edit { it[KEY_ROLE] = role.name }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}