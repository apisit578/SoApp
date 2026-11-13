package com.example.soapp.feature.editpost.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class UpdatePostUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(postId: String, newContent: String): Result<Post> {
        val trimmed = newContent.trim()
        if (trimmed.isEmpty()) {
            return Result.failure(IllegalArgumentException("เนื้อหาแก้ไขต้องไม่เป็นค่าว่าง"))
        }
        return repository.updatePost(postId, trimmed)
    }
}
