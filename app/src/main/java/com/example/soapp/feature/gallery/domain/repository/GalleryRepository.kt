package com.example.soapp.feature.gallery.domain.repository

import com.example.soapp.core.model.MediaItem

interface GalleryRepository {
    suspend fun getUserGallery(userId: String): Result<List<MediaItem>>
}
