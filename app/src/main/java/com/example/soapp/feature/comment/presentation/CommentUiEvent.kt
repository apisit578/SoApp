package com.example.soapp.feature.comment.presentation

sealed interface CommentUiEvent {
    data class UpdateInput(val text: String) : CommentUiEvent
    data object SubmitComment : CommentUiEvent
    data object DismissError : CommentUiEvent
}
