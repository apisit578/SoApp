package com.example.soapp.feature.feed.domain.repository

import com.example.soapp.core.model.Post

interface FeedRepository {
    suspend fun getFeedPosts(): Result<List<Post>>
    suspend fun toggleLikePost(postId: String): Result<Post>
    suspend fun createPost(content: String): Result<Post>
    suspend fun updatePost(postId: String, newContent: String): Result<Post>
    suspend fun deletePost(postId: String): Result<Boolean>
    suspend fun toggleBookmarkPost(postId: String): Result<Post>
    suspend fun getBookmarkedPosts(): Result<List<Post>>
}
