package com.example.soapp.feature.chat.domain.usecase

import com.example.soapp.core.model.ChatMessage
import com.example.soapp.feature.chat.domain.repository.ChatRepository

class SendChatMessageUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(conversationId: String, text: String): Result<ChatMessage> {
        if (text.isBlank()) {
            return Result.failure(IllegalArgumentException("ข้อความต้องไม่เป็นค่าว่าง"))
        }
        return repository.sendMessage(conversationId, text)
    }
}
