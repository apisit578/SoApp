package com.example.soapp.core.model

/**
 * Domain model representing a follower / following user.
 */
data class FollowUser(
    val id: String,
    val name: String,
    val handle: String,
    val bio: String,
    val avatarUrl: String? = null,
    val isFollowing: Boolean = false
)
