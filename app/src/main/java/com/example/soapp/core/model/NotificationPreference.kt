package com.example.soapp.core.model

/**
 * Domain model representing push notification preferences.
 */
data class NotificationPreference(
    val enableLikes: Boolean = true,
    val enableComments: Boolean = true,
    val enableDirectMessages: Boolean = true,
    val enableSystemAnnouncements: Boolean = true
)
