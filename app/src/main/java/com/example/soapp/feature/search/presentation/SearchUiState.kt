package com.example.soapp.feature.search.presentation

import com.example.soapp.core.model.Post

data class SearchUiState(
    val query: String = "",
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
