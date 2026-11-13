package com.example.soapp.feature.feedback.presentation

import com.example.soapp.core.model.FeedbackCategory

data class FeedbackUiState(
    val category: FeedbackCategory = FeedbackCategory.GENERAL,
    val rating: Int = 5,
    val message: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
) {
    val isValid: Boolean get() = message.isNotBlank() && rating in 1..5
}
