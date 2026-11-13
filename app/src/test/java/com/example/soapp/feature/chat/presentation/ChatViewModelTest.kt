package com.example.soapp.feature.chat.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.ChatConversation
import com.example.soapp.core.model.ChatMessage
import com.example.soapp.feature.chat.domain.repository.ChatRepository
import com.example.soapp.feature.chat.domain.usecase.GetChatConversationsUseCase
import com.example.soapp.feature.chat.domain.usecase.GetChatMessagesUseCase
import com.example.soapp.feature.chat.domain.usecase.SendChatMessageUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init should load conversations successfully`() = runTest {
        val testConvs = listOf(
            ChatConversation("conv_1", "Alice", "@alice", "Hello", "1m", 1)
        )
        val fakeRepo = FakeChatRepo(testConvs)
        val viewModel = ChatViewModel(
            getChatConversationsUseCase = GetChatConversationsUseCase(fakeRepo),
            getChatMessagesUseCase = GetChatMessagesUseCase(fakeRepo),
            sendChatMessageUseCase = SendChatMessageUseCase(fakeRepo)
        )

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is ChatUiState.Conversations)
            assertEquals(testConvs, (state as ChatUiState.Conversations).list)
        }
    }
}

private class FakeChatRepo(private val convs: List<ChatConversation>) : ChatRepository {
    override suspend fun getConversations(): Result<List<ChatConversation>> = Result.success(convs)
    override suspend fun getMessages(conversationId: String): Result<List<ChatMessage>> = Result.success(emptyList())
    override suspend fun sendMessage(conversationId: String, text: String): Result<ChatMessage> {
        return Result.success(ChatMessage("m1", conversationId, "Me", "@me", text, "1m", true))
    }
}
