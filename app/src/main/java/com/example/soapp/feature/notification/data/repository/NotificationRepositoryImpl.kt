package com.example.soapp.feature.notification.data.repository

import com.example.soapp.core.model.NotificationItem
import com.example.soapp.core.model.NotificationPreference
import com.example.soapp.feature.notification.data.datasource.MockNotificationDataSource
import com.example.soapp.feature.notification.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val dataSource: MockNotificationDataSource
) : NotificationRepository {

    override suspend fun getNotifications(): Result<List<NotificationItem>> {
        return runCatching { dataSource.fetchNotifications() }
    }

    override suspend fun markAsRead(id: String): Result<Unit> {
        return runCatching { dataSource.markAsRead(id) }
    }

    override suspend fun updatePreferences(preference: NotificationPreference): Result<NotificationPreference> {
        return runCatching { dataSource.updatePreferences(preference) }
    }

    override suspend fun getPreferences(): Result<NotificationPreference> {
        return runCatching { dataSource.fetchPreferences() }
    }
}
