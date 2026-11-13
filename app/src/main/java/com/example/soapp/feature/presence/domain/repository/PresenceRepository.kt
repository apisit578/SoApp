package com.example.soapp.feature.presence.domain.repository

import com.example.soapp.core.model.PresenceStatus
import com.example.soapp.core.model.UserPresence

interface PresenceRepository {
    suspend fun updatePresence(userId: String, status: PresenceStatus, customText: String?): Result<UserPresence>
    suspend fun getPresence(userId: String): Result<UserPresence>
}
