package com.example.soapp.core.model

/**
 * Domain model representing a Topic Tag (hashtag).
 */
data class TopicTag(
    val id: String,
    val name: String,
    val postCount: Int = 0
)
