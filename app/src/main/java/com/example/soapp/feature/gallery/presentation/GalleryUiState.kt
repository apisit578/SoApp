package com.example.soapp.feature.gallery.presentation

import com.example.soapp.core.model.MediaItem

sealed interface GalleryUiState {
    data object Loading : GalleryUiState
    data class Success(
        val items: List<MediaItem>,
        val selectedItem: MediaItem? = null
    ) : GalleryUiState
    data class Error(val message: String) : GalleryUiState
}
