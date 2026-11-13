package com.example.soapp.feature.chat.domain.repository

import com.example.soapp.core.model.ChatConversation
import com.example.soapp.core.model.ChatMessage

interface ChatRepository {
    suspend fun getConversations(): Result<List<ChatConversation>>
    suspend fun getMessages(conversationId: String): Result<List<ChatMessage>>
    suspend fun sendMessage(conversationId: String, text: String): Result<ChatMessage>
}
