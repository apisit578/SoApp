package com.example.soapp.core.model

enum class PresenceStatus(val title: String) {
    ONLINE("ออนไลน์ (Online)"),
    AWAY("ไม่อยู่ (Away)"),
    BUSY("ห้ามรบกวน (Do Not Disturb)"),
    OFFLINE("ออฟไลน์ (Offline)")
}

/**
 * Domain model representing a user presence status.
 */
data class UserPresence(
    val userId: String,
    val status: PresenceStatus,
    val customStatusText: String? = null
)
