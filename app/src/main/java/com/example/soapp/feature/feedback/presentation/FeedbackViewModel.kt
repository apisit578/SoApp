package com.example.soapp.feature.feedback.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.feedback.data.datasource.MockFeedbackDataSourceImpl
import com.example.soapp.feature.feedback.data.repository.FeedbackRepositoryImpl
import com.example.soapp.feature.feedback.domain.usecase.SubmitFeedbackUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedbackViewModel(
    private val submitFeedbackUseCase: SubmitFeedbackUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

    fun onEvent(event: FeedbackUiEvent) {
        when (event) {
            is FeedbackUiEvent.SelectCategory -> _uiState.update { it.copy(category = event.category) }
            is FeedbackUiEvent.SelectRating -> _uiState.update { it.copy(rating = event.rating) }
            is FeedbackUiEvent.UpdateMessage -> _uiState.update { it.copy(message = event.text, errorMessage = null) }
            is FeedbackUiEvent.Submit -> submit()
            is FeedbackUiEvent.DismissError -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun submit() {
        val state = _uiState.value
        if (state.isValid && !state.isSubmitting) {
            viewModelScope.launch {
                _uiState.update { it.copy(isSubmitting = true) }
                submitFeedbackUseCase(state.category, state.rating, state.message)
                    .onSuccess {
                        _uiState.update { it.copy(isSubmitting = false, isSuccess = true) }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(isSubmitting = false, errorMessage = error.message ?: "ไม่สามารถส่งข้อเสนอแนะได้")
                        }
                    }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockFeedbackDataSourceImpl()
                val repository = FeedbackRepositoryImpl(dataSource)
                val useCase = SubmitFeedbackUseCase(repository)
                return FeedbackViewModel(submitFeedbackUseCase = useCase) as T
            }
        }
    }
}
