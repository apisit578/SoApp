package com.example.soapp.feature.feed.presentation

import com.example.soapp.core.model.Post

/**
 * UI State representing the Feed Screen state in Unidirectional Data Flow (UDF).
 */
sealed interface FeedUiState {
    data object Loading : FeedUiState
    data class Success(
        val posts: List<Post>,
        val isRefreshing: Boolean = false
    ) : FeedUiState
    data class Error(val message: String) : FeedUiState
}
