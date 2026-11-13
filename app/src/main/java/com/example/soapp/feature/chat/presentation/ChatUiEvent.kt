package com.example.soapp.feature.chat.presentation

import com.example.soapp.core.model.ChatConversation

sealed interface ChatUiEvent {
    data class SelectConversation(val conversation: ChatConversation) : ChatUiEvent
    data object BackToConversations : ChatUiEvent
    data class UpdateInput(val text: String) : ChatUiEvent
    data object SendMessage : ChatUiEvent
}
