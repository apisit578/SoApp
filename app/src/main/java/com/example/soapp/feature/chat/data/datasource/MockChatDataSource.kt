package com.example.soapp.feature.chat.data.datasource

import com.example.soapp.core.model.ChatConversation
import com.example.soapp.core.model.ChatMessage
import kotlinx.coroutines.delay

interface MockChatDataSource {
    suspend fun fetchConversations(): List<ChatConversation>
    suspend fun fetchMessages(conversationId: String): List<ChatMessage>
    suspend fun sendMessage(conversationId: String, text: String): ChatMessage
}

class MockChatDataSourceImpl : MockChatDataSource {

    private val conversations = mutableListOf(
        ChatConversation("conv_1", "Android Thailand", "@android_th", "ยินดีด้วยครับกับเวอร์ชันใหม่!", "10นาที", 1),
        ChatConversation("conv_2", "Dev Thailand", "@dev_th", "ไว้เจอกันที่งาน Meetup นะครับ", "2ชม.", 0)
    )

    private val messagesMap = mutableMapOf<String, MutableList<ChatMessage>>(
        "conv_1" to mutableListOf(
            ChatMessage("m1", "conv_1", "Android Thailand", "@android_th", "สวัสดีครับอาจารย์ โครงสร้าง SoApp สวยมากครับ!", "15นาที", false),
            ChatMessage("m2", "conv_1", "Android Thailand", "@android_th", "ยินดีด้วยครับกับเวอร์ชันใหม่!", "10นาที", false)
        ),
        "conv_2" to mutableListOf(
            ChatMessage("m3", "conv_2", "Dev Thailand", "@dev_th", "ไว้เจอกันที่งาน Meetup นะครับ", "2ชม.", false)
        )
    )

    override suspend fun fetchConversations(): List<ChatConversation> {
        delay(300)
        return conversations.toList()
    }

    override suspend fun fetchMessages(conversationId: String): List<ChatMessage> {
        delay(200)
        return messagesMap[conversationId]?.toList() ?: emptyList()
    }

    override suspend fun sendMessage(conversationId: String, text: String): ChatMessage {
        delay(300)
        val list = messagesMap.getOrPut(conversationId) { mutableListOf() }
        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderName = "Somchai Dev",
            senderHandle = "@somchai_dev",
            text = text,
            timestamp = "เมื่อครู่",
            isFromMe = true
        )
        list.add(newMsg)

        // Update last message in conversation
        val index = conversations.indexOfFirst { it.id == conversationId }
        if (index != -1) {
            conversations[index] = conversations[index].copy(
                lastMessage = text,
                lastMessageTime = "เมื่อครู่",
                unreadCount = 0
            )
        }
        return newMsg
    }
}
