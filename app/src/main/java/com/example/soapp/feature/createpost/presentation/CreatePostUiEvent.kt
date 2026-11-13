package com.example.soapp.feature.createpost.presentation

sealed interface CreatePostUiEvent {
    data class UpdateContent(val text: String) : CreatePostUiEvent
    data object PublishPost : CreatePostUiEvent
    data object DismissError : CreatePostUiEvent
}
