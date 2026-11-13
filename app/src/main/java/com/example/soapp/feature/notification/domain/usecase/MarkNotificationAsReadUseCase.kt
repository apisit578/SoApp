package com.example.soapp.feature.notification.domain.usecase

import com.example.soapp.feature.notification.domain.repository.NotificationRepository

class MarkNotificationAsReadUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.markAsRead(id)
    }
}
