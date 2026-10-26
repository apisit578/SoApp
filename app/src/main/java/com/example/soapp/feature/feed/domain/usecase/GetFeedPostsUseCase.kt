package com.example.soapp.feature.feed.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

/**
 * UseCase for retrieving feed posts.
 * Encapsulates specific business rules for loading posts.
 */
class GetFeedPostsUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repository.getFeedPosts()
    }
}
