package com.example.soapp.feature.comment.presentation

import com.example.soapp.core.model.Comment

sealed interface CommentUiState {
    data object Loading : CommentUiState
    data class Success(
        val comments: List<Comment>,
        val currentInputText: String = "",
        val isSubmitting: Boolean = false,
        val errorMessage: String? = null
    ) : CommentUiState
    data class Error(val message: String) : CommentUiState
}
