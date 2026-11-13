package com.example.soapp.core.model

/**
 * Domain model representing an Audio Voice Note message payload.
 */
data class AudioMessagePayload(
    val audioUrl: String,
    val durationSeconds: Int,
    val formattedDuration: String
)
