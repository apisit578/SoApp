package com.example.soapp.feature.topic.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class GetPostsByTopicUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(topic: String): Result<List<Post>> {
        val trimmed = topic.trim()
        if (trimmed.isEmpty()) {
            return Result.failure(IllegalArgumentException("Topic ต้องไม่เป็นค่าว่าง"))
        }
        return repository.getPostsByTopic(trimmed)
    }
}
