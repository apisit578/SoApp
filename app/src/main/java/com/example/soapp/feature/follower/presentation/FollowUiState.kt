package com.example.soapp.feature.follower.presentation

import com.example.soapp.core.model.FollowUser

enum class FollowTab { FOLLOWERS, FOLLOWING }

sealed interface FollowUiState {
    data object Loading : FollowUiState
    data class Success(
        val activeTab: FollowTab = FollowTab.FOLLOWERS,
        val followers: List<FollowUser>,
        val following: List<FollowUser>
    ) : FollowUiState
    data class Error(val message: String) : FollowUiState
}
