package com.example.soapp.feature.security.data.datasource

import com.example.soapp.core.model.ActiveSession
import kotlinx.coroutines.delay

interface MockSecurityDataSource {
    suspend fun fetchActiveSessions(): List<ActiveSession>
    suspend fun revokeSession(sessionId: String): Boolean
}

class MockSecurityDataSourceImpl : MockSecurityDataSource {

    private val sessions = mutableListOf(
        ActiveSession("s1", "Google Pixel 8 (อุปกรณ์นี้)", "Bangkok, Thailand", "ใช้งานเมื่อครู่", true),
        ActiveSession("s2", "Chrome Browser (Macintosh)", "Bangkok, Thailand", "2ชม. ที่แล้ว", false),
        ActiveSession("s3", "iPad Pro", "Chiang Mai, Thailand", "3วัน ที่แล้ว", false)
    )

    override suspend fun fetchActiveSessions(): List<ActiveSession> {
        delay(200)
        return sessions.toList()
    }

    override suspend fun revokeSession(sessionId: String): Boolean {
        delay(200)
        return sessions.removeIf { it.id == sessionId }
    }
}
