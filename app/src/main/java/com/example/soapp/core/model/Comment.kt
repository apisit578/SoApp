package com.example.soapp.core.model

/**
 * Domain model representing a comment on a social post.
 */
data class Comment(
    val id: String,
    val postId: String,
    val authorName: String,
    val authorHandle: String,
    val authorAvatarUrl: String? = null,
    val text: String,
    val timeAgo: String
)
