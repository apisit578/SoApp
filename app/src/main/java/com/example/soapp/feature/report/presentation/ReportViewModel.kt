package com.example.soapp.feature.report.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.report.data.datasource.MockReportDataSourceImpl
import com.example.soapp.feature.report.data.repository.ReportRepositoryImpl
import com.example.soapp.feature.report.domain.usecase.ReportPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportViewModel(
    private val postId: String,
    private val reportPostUseCase: ReportPostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    fun onEvent(event: ReportUiEvent) {
        when (event) {
            is ReportUiEvent.SelectReason -> _uiState.update { it.copy(reason = event.reason) }
            is ReportUiEvent.UpdateDetails -> _uiState.update { it.copy(details = event.text) }
            is ReportUiEvent.Submit -> submit()
        }
    }

    private fun submit() {
        val state = _uiState.value
        if (!state.isSubmitting) {
            viewModelScope.launch {
                _uiState.update { it.copy(isSubmitting = true) }
                reportPostUseCase(postId, state.reason, state.details)
                    .onSuccess {
                        _uiState.update { it.copy(isSubmitting = false, isSuccess = true) }
                    }
                    .onFailure { error ->
                        _uiState.update { it.copy(isSubmitting = false, errorMessage = error.message ?: "ไม่สามารถส่งรายงานได้") }
                    }
            }
        }
    }

    companion object {
        fun provideFactory(postId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockReportDataSourceImpl()
                val repository = ReportRepositoryImpl(dataSource)
                val useCase = ReportPostUseCase(repository)
                return ReportViewModel(postId = postId, reportPostUseCase = useCase) as T
            }
        }
    }
}
