package com.example.soapp.feature.feedback.presentation

import com.example.soapp.core.model.FeedbackCategory

sealed interface FeedbackUiEvent {
    data class SelectCategory(val category: FeedbackCategory) : FeedbackUiEvent
    data class SelectRating(val rating: Int) : FeedbackUiEvent
    data class UpdateMessage(val text: String) : FeedbackUiEvent
    data object Submit : FeedbackUiEvent
    data object DismissError : FeedbackUiEvent
}
