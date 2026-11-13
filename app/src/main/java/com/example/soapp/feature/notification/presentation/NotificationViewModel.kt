package com.example.soapp.feature.notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.notification.data.datasource.MockNotificationDataSourceImpl
import com.example.soapp.feature.notification.data.repository.NotificationRepositoryImpl
import com.example.soapp.feature.notification.domain.usecase.GetNotificationsUseCase
import com.example.soapp.feature.notification.domain.usecase.MarkNotificationAsReadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotificationUiState>(NotificationUiState.Loading)
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun onEvent(event: NotificationUiEvent) {
        when (event) {
            is NotificationUiEvent.Refresh -> loadNotifications()
            is NotificationUiEvent.MarkRead -> markAsRead(event.id)
        }
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _uiState.update { NotificationUiState.Loading }
            getNotificationsUseCase()
                .onSuccess { items ->
                    _uiState.update { NotificationUiState.Success(items) }
                }
                .onFailure { error ->
                    _uiState.update { NotificationUiState.Error(error.message ?: "Failed to load notifications") }
                }
        }
    }

    private fun markAsRead(id: String) {
        viewModelScope.launch {
            markNotificationAsReadUseCase(id)
                .onSuccess {
                    loadNotifications()
                }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockNotificationDataSourceImpl()
                val repository = NotificationRepositoryImpl(dataSource)
                val getNotifications = GetNotificationsUseCase(repository)
                val markRead = MarkNotificationAsReadUseCase(repository)
                return NotificationViewModel(
                    getNotificationsUseCase = getNotifications,
                    markNotificationAsReadUseCase = markRead
                ) as T
            }
        }
    }
}
