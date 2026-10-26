package com.example.soapp.feature.feed.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

/**
 * UseCase for toggling like status on a post.
 */
class ToggleLikePostUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(postId: String): Result<Post> {
        return repository.toggleLikePost(postId)
    }
}
