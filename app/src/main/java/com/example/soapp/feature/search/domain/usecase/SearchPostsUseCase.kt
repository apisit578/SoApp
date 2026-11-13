package com.example.soapp.feature.search.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class SearchPostsUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(query: String): Result<List<Post>> {
        return runCatching {
            val allPosts = repository.getFeedPosts().getOrThrow()
            val trimmed = query.trim()
            if (trimmed.isEmpty()) {
                allPosts
            } else {
                allPosts.filter { post ->
                    post.content.contains(trimmed, ignoreCase = true) ||
                            post.authorName.contains(trimmed, ignoreCase = true) ||
                            post.authorHandle.contains(trimmed, ignoreCase = true)
                }
            }
        }
    }
}
