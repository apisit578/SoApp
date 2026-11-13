package com.example.soapp.feature.chat.domain.usecase

import com.example.soapp.core.model.ChatConversation
import com.example.soapp.feature.chat.domain.repository.ChatRepository

class GetChatConversationsUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Result<List<ChatConversation>> {
        return repository.getConversations()
    }
}
