package com.example.soapp.feature.bookmark.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.bookmark.domain.usecase.GetBookmarkedPostsUseCase
import com.example.soapp.feature.bookmark.domain.usecase.ToggleBookmarkUseCase
import com.example.soapp.feature.feed.data.datasource.MockFeedDataSourceImpl
import com.example.soapp.feature.feed.data.repository.FeedRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val getBookmarkedPostsUseCase: GetBookmarkedPostsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookmarkUiState>(BookmarkUiState.Loading)
    val uiState: StateFlow<BookmarkUiState> = _uiState.asStateFlow()

    init {
        loadBookmarks()
    }

    fun onEvent(event: BookmarkUiEvent) {
        when (event) {
            is BookmarkUiEvent.Refresh -> loadBookmarks()
            is BookmarkUiEvent.RemoveBookmark -> removeBookmark(event.postId)
        }
    }

    fun loadBookmarks() {
        viewModelScope.launch {
            _uiState.update { BookmarkUiState.Loading }
            getBookmarkedPostsUseCase()
                .onSuccess { posts ->
                    _uiState.update { BookmarkUiState.Success(posts) }
                }
                .onFailure { error ->
                    _uiState.update { BookmarkUiState.Error(error.message ?: "Failed to load bookmarks") }
                }
        }
    }

    private fun removeBookmark(postId: String) {
        viewModelScope.launch {
            toggleBookmarkUseCase(postId)
                .onSuccess {
                    loadBookmarks()
                }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockFeedDataSourceImpl()
                val repository = FeedRepositoryImpl(dataSource)
                val getBookmarks = GetBookmarkedPostsUseCase(repository)
                val toggleBookmark = ToggleBookmarkUseCase(repository)
                return BookmarkViewModel(
                    getBookmarkedPostsUseCase = getBookmarks,
                    toggleBookmarkUseCase = toggleBookmark
                ) as T
            }
        }
    }
}
