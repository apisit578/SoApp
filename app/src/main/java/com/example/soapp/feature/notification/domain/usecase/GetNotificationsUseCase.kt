package com.example.soapp.feature.notification.domain.usecase

import com.example.soapp.core.model.NotificationItem
import com.example.soapp.feature.notification.domain.repository.NotificationRepository

class GetNotificationsUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(): Result<List<NotificationItem>> {
        return repository.getNotifications()
    }
}
