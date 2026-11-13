package com.example.soapp.feature.notification.presentation

sealed interface NotificationUiEvent {
    data object Refresh : NotificationUiEvent
    data class MarkRead(val id: String) : NotificationUiEvent
}
