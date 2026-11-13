package com.example.soapp.feature.gallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.core.model.MediaItem
import com.example.soapp.feature.gallery.data.datasource.MockGalleryDataSourceImpl
import com.example.soapp.feature.gallery.data.repository.GalleryRepositoryImpl
import com.example.soapp.feature.gallery.domain.usecase.GetUserGalleryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val userId: String,
    private val getUserGalleryUseCase: GetUserGalleryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Loading)
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    init {
        loadGallery()
    }

    fun loadGallery() {
        viewModelScope.launch {
            _uiState.update { GalleryUiState.Loading }
            getUserGalleryUseCase(userId)
                .onSuccess { items ->
                    _uiState.update { GalleryUiState.Success(items) }
                }
                .onFailure { error ->
                    _uiState.update { GalleryUiState.Error(error.message ?: "Failed to load gallery") }
                }
        }
    }

    fun selectItem(item: MediaItem?) {
        _uiState.update { state ->
            if (state is GalleryUiState.Success) {
                state.copy(selectedItem = item)
            } else state
        }
    }

    companion object {
        fun provideFactory(userId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockGalleryDataSourceImpl()
                val repository = GalleryRepositoryImpl(dataSource)
                val useCase = GetUserGalleryUseCase(repository)
                return GalleryViewModel(userId = userId, getUserGalleryUseCase = useCase) as T
            }
        }
    }
}
