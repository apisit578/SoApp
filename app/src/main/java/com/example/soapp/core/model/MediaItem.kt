package com.example.soapp.core.model

/**
 * Domain model representing a user gallery media item.
 */
data class MediaItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val timestamp: String
)
