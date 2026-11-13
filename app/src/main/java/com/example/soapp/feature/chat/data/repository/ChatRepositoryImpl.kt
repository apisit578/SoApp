package com.example.soapp.feature.chat.data.repository

import com.example.soapp.core.model.ChatConversation
import com.example.soapp.core.model.ChatMessage
import com.example.soapp.feature.chat.data.datasource.MockChatDataSource
import com.example.soapp.feature.chat.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val dataSource: MockChatDataSource
) : ChatRepository {

    override suspend fun getConversations(): Result<List<ChatConversation>> {
        return runCatching { dataSource.fetchConversations() }
    }

    override suspend fun getMessages(conversationId: String): Result<List<ChatMessage>> {
        return runCatching { dataSource.fetchMessages(conversationId) }
    }

    override suspend fun sendMessage(conversationId: String, text: String): Result<ChatMessage> {
        return runCatching { dataSource.sendMessage(conversationId, text) }
    }
}
