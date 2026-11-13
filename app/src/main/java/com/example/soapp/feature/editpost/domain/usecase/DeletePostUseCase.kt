package com.example.soapp.feature.editpost.domain.usecase

import com.example.soapp.feature.feed.domain.repository.FeedRepository

class DeletePostUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(postId: String): Result<Boolean> {
        return repository.deletePost(postId)
    }
}
