package com.example.soapp.feature.gallery.data.datasource

import com.example.soapp.core.model.MediaItem
import kotlinx.coroutines.delay

interface MockGalleryDataSource {
    suspend fun fetchGallery(userId: String): List<MediaItem>
}

class MockGalleryDataSourceImpl : MockGalleryDataSource {

    private val mediaList = listOf(
        MediaItem("m1", "ภาพเปิดตัว SoApp Pro", "https://picsum.photos/400/400?random=1", "3วัน"),
        MediaItem("m2", "บรรยากาศการทดสอบระบบ", "https://picsum.photos/400/400?random=2", "1สัปดาห์"),
        MediaItem("m3", "Kotlin & Compose Meetup", "https://picsum.photos/400/400?random=3", "2สัปดาห์"),
        MediaItem("m4", "Clean Architecture Workflow", "https://picsum.photos/400/400?random=4", "1เดือน"),
        MediaItem("m5", "Jetpack Compose UI", "https://picsum.photos/400/400?random=5", "2เดือน"),
        MediaItem("m6", "JVM Unit Testing Guide", "https://picsum.photos/400/400?random=6", "3เดือน")
    )

    override suspend fun fetchGallery(userId: String): List<MediaItem> {
        delay(400)
        return mediaList
    }
}
