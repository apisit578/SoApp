package com.example.soapp.feature.bookmark.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class ToggleBookmarkUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(postId: String): Result<Post> {
        return repository.toggleBookmarkPost(postId)
    }
}
