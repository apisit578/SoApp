package com.example.soapp.feature.security.domain.usecase

import com.example.soapp.core.model.ActiveSession
import com.example.soapp.feature.security.domain.repository.SecurityRepository

class GetActiveSessionsUseCase(
    private val repository: SecurityRepository
) {
    suspend operator fun invoke(): Result<List<ActiveSession>> {
        return repository.getActiveSessions()
    }
}
