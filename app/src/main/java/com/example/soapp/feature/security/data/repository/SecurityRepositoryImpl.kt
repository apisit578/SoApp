package com.example.soapp.feature.security.data.repository

import com.example.soapp.core.model.ActiveSession
import com.example.soapp.feature.security.data.datasource.MockSecurityDataSource
import com.example.soapp.feature.security.domain.repository.SecurityRepository

class SecurityRepositoryImpl(
    private val dataSource: MockSecurityDataSource
) : SecurityRepository {

    override suspend fun getActiveSessions(): Result<List<ActiveSession>> {
        return runCatching { dataSource.fetchActiveSessions() }
    }

    override suspend fun revokeSession(sessionId: String): Result<Boolean> {
        return runCatching { dataSource.revokeSession(sessionId) }
    }
}
