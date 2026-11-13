package com.example.soapp.feature.search.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.SearchCategory
import com.example.soapp.feature.feed.domain.repository.FeedRepository

class SearchCategorizedUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(query: String, category: SearchCategory): Result<List<Post>> {
        return runCatching {
            val allPosts = repository.getFeedPosts().getOrThrow()
            val trimmed = query.trim()
            val filteredByQuery = if (trimmed.isEmpty()) {
                allPosts
            } else {
                allPosts.filter { post ->
                    post.content.contains(trimmed, ignoreCase = true) ||
                            post.authorName.contains(trimmed, ignoreCase = true) ||
                            post.authorHandle.contains(trimmed, ignoreCase = true)
                }
            }

            when (category) {
                SearchCategory.ALL -> filteredByQuery
                SearchCategory.POSTS -> filteredByQuery.filter { it.content.contains(trimmed, ignoreCase = true) }
                SearchCategory.USERS -> filteredByQuery.filter { it.authorName.contains(trimmed, ignoreCase = true) || it.authorHandle.contains(trimmed, ignoreCase = true) }
            }
        }
    }
}
