package com.example.soapp.feature.profile.presentation

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.UserProfile

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val profile: UserProfile,
        val posts: List<Post>
    ) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}
