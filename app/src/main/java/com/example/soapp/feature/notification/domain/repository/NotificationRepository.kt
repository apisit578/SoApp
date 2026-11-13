package com.example.soapp.feature.notification.domain.repository

import com.example.soapp.core.model.NotificationItem

interface NotificationRepository {
    suspend fun getNotifications(): Result<List<NotificationItem>>
    suspend fun markAsRead(id: String): Result<Unit>
}
