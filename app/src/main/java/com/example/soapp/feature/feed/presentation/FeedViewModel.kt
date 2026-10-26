package com.example.soapp.feature.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.feed.data.datasource.MockFeedDataSourceImpl
import com.example.soapp.feature.feed.data.repository.FeedRepositoryImpl
import com.example.soapp.feature.feed.domain.usecase.GetFeedPostsUseCase
import com.example.soapp.feature.feed.domain.usecase.ToggleLikePostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeedPostsUseCase: GetFeedPostsUseCase,
    private val toggleLikePostUseCase: ToggleLikePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun onEvent(event: FeedUiEvent) {
        when (event) {
            is FeedUiEvent.Refresh -> loadPosts()
            is FeedUiEvent.ToggleLike -> toggleLike(event.postId)
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _uiState.update { FeedUiState.Loading }
            getFeedPostsUseCase()
                .onSuccess { posts ->
                    _uiState.update { FeedUiState.Success(posts = posts) }
                }
                .onFailure { error ->
                    _uiState.update { FeedUiState.Error(error.message ?: "Failed to load posts") }
                }
        }
    }

    private fun toggleLike(postId: String) {
        val currentState = _uiState.value
        if (currentState is FeedUiState.Success) {
            viewModelScope.launch {
                toggleLikePostUseCase(postId)
                    .onSuccess { updatedPost ->
                        _uiState.update { state ->
                            if (state is FeedUiState.Success) {
                                val updatedPosts = state.posts.map { post ->
                                    if (post.id == postId) updatedPost else post
                                }
                                state.copy(posts = updatedPosts)
                            } else {
                                state
                            }
                        }
                    }
                    .onFailure {
                        // Handle error or toast notification
                    }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockFeedDataSourceImpl()
                val repository = FeedRepositoryImpl(dataSource)
                val getFeedPostsUseCase = GetFeedPostsUseCase(repository)
                val toggleLikePostUseCase = ToggleLikePostUseCase(repository)
                return FeedViewModel(
                    getFeedPostsUseCase = getFeedPostsUseCase,
                    toggleLikePostUseCase = toggleLikePostUseCase
                ) as T
            }
        }
    }
}
