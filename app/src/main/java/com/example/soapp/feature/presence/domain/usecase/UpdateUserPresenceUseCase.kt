package com.example.soapp.feature.presence.domain.usecase

import com.example.soapp.core.model.PresenceStatus
import com.example.soapp.core.model.UserPresence
import com.example.soapp.feature.presence.domain.repository.PresenceRepository

class UpdateUserPresenceUseCase(
    private val repository: PresenceRepository
) {
    suspend operator fun invoke(userId: String, status: PresenceStatus, customText: String? = null): Result<UserPresence> {
        if (userId.isBlank()) {
            return Result.failure(IllegalArgumentException("User ID ต้องไม่เป็นค่าว่าง"))
        }
        return repository.updatePresence(userId, status, customText?.trim())
    }
}
