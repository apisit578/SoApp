package com.example.soapp.feature.notification.domain.repository

import com.example.soapp.core.model.NotificationItem
import com.example.soapp.core.model.NotificationPreference

interface NotificationRepository {
    suspend fun getNotifications(): Result<List<NotificationItem>>
    suspend fun markAsRead(id: String): Result<Unit>
    suspend fun updatePreferences(preference: NotificationPreference): Result<NotificationPreference>
    suspend fun getPreferences(): Result<NotificationPreference>
}
