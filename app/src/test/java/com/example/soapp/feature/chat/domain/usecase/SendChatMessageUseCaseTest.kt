package com.example.soapp.feature.chat.domain.usecase

import com.example.soapp.core.model.ChatConversation
import com.example.soapp.core.model.ChatMessage
import com.example.soapp.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SendChatMessageUseCaseTest {

    @Test
    fun `invoke with blank text should return failure`() = runTest {
        val fakeRepo = FakeChatRepo()
        val useCase = SendChatMessageUseCase(fakeRepo)

        val result = useCase(conversationId = "conv_1", text = "   ")

        assertTrue(result.isFailure)
        assertEquals("ข้อความต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid text should send message successfully`() = runTest {
        val fakeRepo = FakeChatRepo()
        val useCase = SendChatMessageUseCase(fakeRepo)

        val result = useCase(conversationId = "conv_1", text = "สวัสดีครับ")

        assertTrue(result.isSuccess)
        assertEquals("สวัสดีครับ", result.getOrNull()?.text)
    }
}

private class FakeChatRepo : ChatRepository {
    private val messages = mutableListOf<ChatMessage>()

    override suspend fun getConversations(): Result<List<ChatConversation>> = Result.success(emptyList())
    override suspend fun getMessages(conversationId: String): Result<List<ChatMessage>> = Result.success(messages)
    override suspend fun sendMessage(conversationId: String, text: String): Result<ChatMessage> {
        val newMsg = ChatMessage("m1", conversationId, "Somchai", "@somchai", text, "เมื่อครู่", true)
        messages.add(newMsg)
        return Result.success(newMsg)
    }
}
