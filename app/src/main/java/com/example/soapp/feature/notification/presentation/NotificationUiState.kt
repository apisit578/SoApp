package com.example.soapp.feature.notification.presentation

import com.example.soapp.core.model.NotificationItem

sealed interface NotificationUiState {
    data object Loading : NotificationUiState
    data class Success(val notifications: List<NotificationItem>) : NotificationUiState
    data class Error(val message: String) : NotificationUiState
}
