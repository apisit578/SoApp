package com.example.soapp.feature.follower.presentation

sealed interface FollowUiEvent {
    data class SelectTab(val tab: FollowTab) : FollowUiEvent
    data class ToggleFollow(val targetUserId: String) : FollowUiEvent
}
