package com.example.soapp.feature.presence.data.repository

import com.example.soapp.core.model.PresenceStatus
import com.example.soapp.core.model.UserPresence
import com.example.soapp.feature.presence.data.datasource.MockPresenceDataSource
import com.example.soapp.feature.presence.domain.repository.PresenceRepository

class PresenceRepositoryImpl(
    private val dataSource: MockPresenceDataSource
) : PresenceRepository {

    override suspend fun updatePresence(userId: String, status: PresenceStatus, customText: String?): Result<UserPresence> {
        return runCatching { dataSource.updatePresence(userId, status, customText) }
    }

    override suspend fun getPresence(userId: String): Result<UserPresence> {
        return runCatching { dataSource.fetchPresence(userId) }
    }
}
