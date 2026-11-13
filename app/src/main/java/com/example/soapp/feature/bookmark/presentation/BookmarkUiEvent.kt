package com.example.soapp.feature.bookmark.presentation

sealed interface BookmarkUiEvent {
    data object Refresh : BookmarkUiEvent
    data class RemoveBookmark(val postId: String) : BookmarkUiEvent
}
