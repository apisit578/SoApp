package com.example.soapp.core.model

/**
 * Domain model representing a User Profile.
 */
data class UserProfile(
    val id: String,
    val name: String,
    val handle: String,
    val bio: String,
    val avatarUrl: String? = null,
    val postCount: Int = 0,
    val followerCount: Int = 0,
    val followingCount: Int = 0
)
