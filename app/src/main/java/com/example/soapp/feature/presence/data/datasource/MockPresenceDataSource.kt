package com.example.soapp.feature.presence.data.datasource

import com.example.soapp.core.model.PresenceStatus
import com.example.soapp.core.model.UserPresence
import kotlinx.coroutines.delay

interface MockPresenceDataSource {
    suspend fun updatePresence(userId: String, status: PresenceStatus, customText: String?): UserPresence
    suspend fun fetchPresence(userId: String): UserPresence
}

class MockPresenceDataSourceImpl : MockPresenceDataSource {

    private val presenceMap = mutableMapOf<String, UserPresence>()

    override suspend fun updatePresence(userId: String, status: PresenceStatus, customText: String?): UserPresence {
        delay(200)
        val presence = UserPresence(userId, status, customText)
        presenceMap[userId] = presence
        return presence
    }

    override suspend fun fetchPresence(userId: String): UserPresence {
        delay(200)
        return presenceMap[userId] ?: UserPresence(userId, PresenceStatus.ONLINE)
    }
}
