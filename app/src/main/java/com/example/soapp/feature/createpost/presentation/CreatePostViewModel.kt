package com.example.soapp.feature.createpost.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.createpost.domain.usecase.CreatePostUseCase
import com.example.soapp.feature.feed.data.datasource.MockFeedDataSourceImpl
import com.example.soapp.feature.feed.data.repository.FeedRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState: StateFlow<CreatePostUiState> = _uiState.asStateFlow()

    fun onEvent(event: CreatePostUiEvent) {
        when (event) {
            is CreatePostUiEvent.UpdateContent -> updateContent(event.text)
            is CreatePostUiEvent.PublishPost -> publishPost()
            is CreatePostUiEvent.DismissError -> dismissError()
        }
    }

    private fun updateContent(text: String) {
        _uiState.update { it.copy(content = text, errorMessage = null) }
    }

    private fun publishPost() {
        val currentState = _uiState.value
        if (currentState.isValid && !currentState.isPublishing) {
            viewModelScope.launch {
                _uiState.update { it.copy(isPublishing = true) }
                createPostUseCase(currentState.content)
                    .onSuccess {
                        _uiState.update { it.copy(isPublishing = false, isSuccess = true) }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isPublishing = false,
                                errorMessage = error.message ?: "ไม่สามารถโพสต์ได้"
                            )
                        }
                    }
            }
        }
    }

    private fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockFeedDataSourceImpl()
                val repository = FeedRepositoryImpl(dataSource)
                val useCase = CreatePostUseCase(repository)
                return CreatePostViewModel(createPostUseCase = useCase) as T
            }
        }
    }
}
