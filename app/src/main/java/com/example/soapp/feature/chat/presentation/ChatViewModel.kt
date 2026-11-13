package com.example.soapp.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.core.model.ChatConversation
import com.example.soapp.feature.chat.data.datasource.MockChatDataSourceImpl
import com.example.soapp.feature.chat.data.repository.ChatRepositoryImpl
import com.example.soapp.feature.chat.domain.usecase.GetChatConversationsUseCase
import com.example.soapp.feature.chat.domain.usecase.GetChatMessagesUseCase
import com.example.soapp.feature.chat.domain.usecase.SendChatMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getChatConversationsUseCase: GetChatConversationsUseCase,
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        loadConversations()
    }

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SelectConversation -> selectConversation(event.conversation)
            is ChatUiEvent.BackToConversations -> loadConversations()
            is ChatUiEvent.UpdateInput -> updateInput(event.text)
            is ChatUiEvent.SendMessage -> sendMessage()
        }
    }

    private fun loadConversations() {
        viewModelScope.launch {
            _uiState.update { ChatUiState.Loading }
            getChatConversationsUseCase()
                .onSuccess { list ->
                    _uiState.update { ChatUiState.Conversations(list) }
                }
                .onFailure { error ->
                    _uiState.update { ChatUiState.Error(error.message ?: "Failed to load chats") }
                }
        }
    }

    private fun selectConversation(conversation: ChatConversation) {
        viewModelScope.launch {
            _uiState.update { ChatUiState.Loading }
            getChatMessagesUseCase(conversation.id)
                .onSuccess { messages ->
                    _uiState.update {
                        ChatUiState.ActiveThread(
                            conversation = conversation,
                            messages = messages
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { ChatUiState.Error(error.message ?: "Failed to load thread") }
                }
        }
    }

    private fun updateInput(text: String) {
        _uiState.update { state ->
            if (state is ChatUiState.ActiveThread) {
                state.copy(currentInputText = text)
            } else state
        }
    }

    private fun sendMessage() {
        val currentState = _uiState.value
        if (currentState is ChatUiState.ActiveThread && !currentState.isSending) {
            val textToSend = currentState.currentInputText
            val convId = currentState.conversation.id
            viewModelScope.launch {
                _uiState.update { (it as ChatUiState.ActiveThread).copy(isSending = true) }
                sendChatMessageUseCase(convId, textToSend)
                    .onSuccess { newMsg ->
                        _uiState.update { state ->
                            if (state is ChatUiState.ActiveThread) {
                                state.copy(
                                    messages = state.messages + newMsg,
                                    currentInputText = "",
                                    isSending = false
                                )
                            } else state
                        }
                    }
                    .onFailure {
                        _uiState.update { state ->
                            if (state is ChatUiState.ActiveThread) {
                                state.copy(isSending = false)
                            } else state
                        }
                    }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockChatDataSourceImpl()
                val repository = ChatRepositoryImpl(dataSource)
                val getConvs = GetChatConversationsUseCase(repository)
                val getMsgs = GetChatMessagesUseCase(repository)
                val sendMsg = SendChatMessageUseCase(repository)
                return ChatViewModel(
                    getChatConversationsUseCase = getConvs,
                    getChatMessagesUseCase = getMsgs,
                    sendChatMessageUseCase = sendMsg
                ) as T
            }
        }
    }
}
