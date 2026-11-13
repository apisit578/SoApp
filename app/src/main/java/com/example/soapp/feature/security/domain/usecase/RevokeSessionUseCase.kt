package com.example.soapp.feature.security.domain.usecase

import com.example.soapp.feature.security.domain.repository.SecurityRepository

class RevokeSessionUseCase(
    private val repository: SecurityRepository
) {
    suspend operator fun invoke(sessionId: String): Result<Boolean> {
        if (sessionId.isBlank()) {
            return Result.failure(IllegalArgumentException("Session ID ต้องไม่เป็นค่าว่าง"))
        }
        return repository.revokeSession(sessionId)
    }
}
