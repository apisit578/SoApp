package com.example.soapp.core.model

/**
 * Domain model representing a Social Post.
 */
data class Post(
    val id: String,
    val authorName: String,
    val authorHandle: String,
    val authorAvatarUrl: String? = null,
    val content: String,
    val timeAgo: String,
    val likeCount: Int,
    val isLiked: Boolean = false,
    val commentCount: Int = 0
)
