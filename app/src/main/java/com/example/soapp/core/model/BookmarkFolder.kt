package com.example.soapp.core.model

/**
 * Domain model representing a custom folder for organizing bookmarked posts.
 */
data class BookmarkFolder(
    val id: String,
    val name: String,
    val postCount: Int = 0
)
