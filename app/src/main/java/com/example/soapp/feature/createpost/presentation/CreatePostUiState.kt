package com.example.soapp.feature.createpost.presentation

data class CreatePostUiState(
    val content: String = "",
    val isPublishing: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
) {
    val remainingChars: Int get() = 280 - content.length
    val isValid: Boolean get() = content.isNotBlank() && content.length <= 280
}
