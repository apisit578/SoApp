package com.example.soapp.feature.bookmark.presentation

import com.example.soapp.core.model.Post

sealed interface BookmarkUiState {
    data object Loading : BookmarkUiState
    data class Success(val bookmarkedPosts: List<Post>) : BookmarkUiState
    data class Error(val message: String) : BookmarkUiState
}
