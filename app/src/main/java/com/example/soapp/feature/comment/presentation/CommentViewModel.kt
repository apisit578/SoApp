package com.example.soapp.feature.comment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.comment.data.datasource.MockCommentDataSourceImpl
import com.example.soapp.feature.comment.data.repository.CommentRepositoryImpl
import com.example.soapp.feature.comment.domain.usecase.AddCommentUseCase
import com.example.soapp.feature.comment.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommentViewModel(
    private val postId: String,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val addCommentUseCase: AddCommentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CommentUiState>(CommentUiState.Loading)
    val uiState: StateFlow<CommentUiState> = _uiState.asStateFlow()

    init {
        loadComments()
    }

    fun onEvent(event: CommentUiEvent) {
        when (event) {
            is CommentUiEvent.UpdateInput -> updateInput(event.text)
            is CommentUiEvent.SubmitComment -> submitComment()
            is CommentUiEvent.DismissError -> dismissError()
        }
    }

    private fun loadComments() {
        viewModelScope.launch {
            _uiState.update { CommentUiState.Loading }
            getCommentsUseCase(postId)
                .onSuccess { comments ->
                    _uiState.update { CommentUiState.Success(comments = comments) }
                }
                .onFailure { error ->
                    _uiState.update { CommentUiState.Error(error.message ?: "Failed to load comments") }
                }
        }
    }

    private fun updateInput(text: String) {
        _uiState.update { state ->
            if (state is CommentUiState.Success) {
                state.copy(currentInputText = text)
            } else state
        }
    }

    private fun submitComment() {
        val currentState = _uiState.value
        if (currentState is CommentUiState.Success && !currentState.isSubmitting) {
            val textToSubmit = currentState.currentInputText
            viewModelScope.launch {
                _uiState.update { (it as CommentUiState.Success).copy(isSubmitting = true) }
                addCommentUseCase(postId, textToSubmit)
                    .onSuccess { newComment ->
                        _uiState.update { state ->
                            if (state is CommentUiState.Success) {
                                state.copy(
                                    comments = listOf(newComment) + state.comments,
                                    currentInputText = "",
                                    isSubmitting = false
                                )
                            } else state
                        }
                    }
                    .onFailure { error ->
                        _uiState.update { state ->
                            if (state is CommentUiState.Success) {
                                state.copy(
                                    isSubmitting = false,
                                    errorMessage = error.message ?: "ไม่สามารถส่งความคิดเห็นได้"
                                )
                            } else state
                        }
                    }
            }
        }
    }

    private fun dismissError() {
        _uiState.update { state ->
            if (state is CommentUiState.Success) {
                state.copy(errorMessage = null)
            } else state
        }
    }

    companion object {
        fun provideFactory(postId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockCommentDataSourceImpl()
                val repository = CommentRepositoryImpl(dataSource)
                val getComments = GetCommentsUseCase(repository)
                val addComment = AddCommentUseCase(repository)
                return CommentViewModel(
                    postId = postId,
                    getCommentsUseCase = getComments,
                    addCommentUseCase = addComment
                ) as T
            }
        }
    }
}
