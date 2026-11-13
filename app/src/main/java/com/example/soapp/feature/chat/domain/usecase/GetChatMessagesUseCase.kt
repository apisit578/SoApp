package com.example.soapp.feature.chat.domain.usecase

import com.example.soapp.core.model.ChatMessage
import com.example.soapp.feature.chat.domain.repository.ChatRepository

class GetChatMessagesUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(conversationId: String): Result<List<ChatMessage>> {
        return repository.getMessages(conversationId)
    }
}
