package com.example.soapp.feature.notification.domain.usecase

import com.example.soapp.core.model.NotificationPreference
import com.example.soapp.feature.notification.domain.repository.NotificationRepository

class UpdateNotificationPreferencesUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(preference: NotificationPreference): Result<NotificationPreference> {
        return repository.updatePreferences(preference)
    }
}
