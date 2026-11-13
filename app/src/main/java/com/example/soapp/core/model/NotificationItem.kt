package com.example.soapp.core.model

enum class NotificationType {
    LIKE,
    COMMENT,
    SYSTEM
}

/**
 * Domain model representing a user notification item.
 */
data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val actorName: String,
    val message: String,
    val timeAgo: String,
    val isRead: Boolean = false
)
