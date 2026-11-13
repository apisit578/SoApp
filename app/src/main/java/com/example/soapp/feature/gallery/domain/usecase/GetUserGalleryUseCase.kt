package com.example.soapp.feature.gallery.domain.usecase

import com.example.soapp.core.model.MediaItem
import com.example.soapp.feature.gallery.domain.repository.GalleryRepository

class GetUserGalleryUseCase(
    private val repository: GalleryRepository
) {
    suspend operator fun invoke(userId: String): Result<List<MediaItem>> {
        return repository.getUserGallery(userId)
    }
}
