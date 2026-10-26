package com.example.soapp.feature.feed.domain.repository

import com.example.soapp.core.model.Post

/**
 * Domain Repository interface for Feed data operations.
 */
interface FeedRepository {
    suspend fun getFeedPosts(): Result<List<Post>>
    suspend fun toggleLikePost(postId: String): Result<Post>
}
