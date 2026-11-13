package com.example.soapp.feature.chat.presentation

import com.example.soapp.core.model.ChatConversation
import com.example.soapp.core.model.ChatMessage

sealed interface ChatUiState {
    data object Loading : ChatUiState
    data class Conversations(val list: List<ChatConversation>) : ChatUiState
    data class ActiveThread(
        val conversation: ChatConversation,
        val messages: List<ChatMessage>,
        val currentInputText: String = "",
        val isSending: Boolean = false
    ) : ChatUiState
    data class Error(val message: String) : ChatUiState
}
