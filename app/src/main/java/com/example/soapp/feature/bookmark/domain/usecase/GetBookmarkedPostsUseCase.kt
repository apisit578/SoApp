package com.example.soapp.feature.bookmark.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class GetBookmarkedPostsUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repository.getBookmarkedPosts()
    }
}
