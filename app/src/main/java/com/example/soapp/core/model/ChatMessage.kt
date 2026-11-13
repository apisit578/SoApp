package com.example.soapp.core.model

/**
 * Domain model representing a Direct Message / Chat conversation.
 */
data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderName: String,
    val senderHandle: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean = false
)

data class ChatConversation(
    val id: String,
    val participantName: String,
    val participantHandle: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0
)
