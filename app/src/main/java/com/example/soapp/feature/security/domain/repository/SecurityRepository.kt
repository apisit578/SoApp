package com.example.soapp.feature.security.domain.repository

import com.example.soapp.core.model.ActiveSession

interface SecurityRepository {
    suspend fun getActiveSessions(): Result<List<ActiveSession>>
    suspend fun revokeSession(sessionId: String): Result<Boolean>
}
