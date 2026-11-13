package com.example.soapp.feature.gallery.data.repository

import com.example.soapp.core.model.MediaItem
import com.example.soapp.feature.gallery.data.datasource.MockGalleryDataSource
import com.example.soapp.feature.gallery.domain.repository.GalleryRepository

class GalleryRepositoryImpl(
    private val dataSource: MockGalleryDataSource
) : GalleryRepository {

    override suspend fun getUserGallery(userId: String): Result<List<MediaItem>> {
        return runCatching { dataSource.fetchGallery(userId) }
    }
}
