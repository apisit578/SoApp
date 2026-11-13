package com.example.soapp.core.model

/**
 * Domain model representing an active user login session / device.
 */
data class ActiveSession(
    val id: String,
    val deviceName: String,
    val location: String,
    val lastActive: String,
    val isCurrentDevice: Boolean = false
)
