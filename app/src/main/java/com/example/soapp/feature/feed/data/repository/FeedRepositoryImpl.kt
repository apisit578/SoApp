package com.example.soapp.feature.feed.data.repository

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.data.datasource.MockFeedDataSource
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val dataSource: MockFeedDataSource
) : FeedRepository {

    override suspend fun getFeedPosts(): Result<List<Post>> {
        return runCatching { dataSource.fetchPosts() }
    }

    override suspend fun toggleLikePost(postId: String): Result<Post> {
        return runCatching { dataSource.toggleLike(postId) }
    }

    override suspend fun createPost(content: String): Result<Post> {
        return runCatching { dataSource.createPost(content) }
    }

    override suspend fun toggleBookmarkPost(postId: String): Result<Post> {
        return runCatching { dataSource.toggleBookmark(postId) }
    }

    override suspend fun getBookmarkedPosts(): Result<List<Post>> {
        return runCatching { dataSource.fetchBookmarkedPosts() }
    }
}
