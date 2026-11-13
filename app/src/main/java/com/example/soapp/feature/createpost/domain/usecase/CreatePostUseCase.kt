package com.example.soapp.feature.createpost.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class CreatePostUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(content: String): Result<Post> {
        val trimmed = content.trim()
        if (trimmed.isEmpty()) {
            return Result.failure(IllegalArgumentException("เนื้อหาโพสต์ต้องไม่เป็นค่าว่าง"))
        }
        if (trimmed.length > 280) {
            return Result.failure(IllegalArgumentException("ข้อความยาวเกิน 280 ตัวอักษร"))
        }
        return repository.createPost(trimmed)
    }
}
