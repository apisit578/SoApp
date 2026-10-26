package com.example.soapp.feature.feed.presentation

/**
 * User Intent / Events triggered from the UI in UDF architecture.
 */
sealed interface FeedUiEvent {
    data object Refresh : FeedUiEvent
    data class ToggleLike(val postId: String) : FeedUiEvent
}
