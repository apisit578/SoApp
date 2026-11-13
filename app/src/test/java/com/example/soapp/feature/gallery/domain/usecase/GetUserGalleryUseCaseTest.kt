package com.example.soapp.feature.gallery.domain.usecase

import com.example.soapp.core.model.MediaItem
import com.example.soapp.feature.gallery.domain.repository.GalleryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetUserGalleryUseCaseTest {

    @Test
    fun `invoke should return user media gallery list`() = runTest {
        val expected = listOf(
            MediaItem("m1", "Test Photo", "http://example.com/1.jpg", "1d")
        )
        val fakeRepo = FakeGalleryRepo(expected)
        val useCase = GetUserGalleryUseCase(fakeRepo)

        val result = useCase("user_1")

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }
}

private class FakeGalleryRepo(private val items: List<MediaItem>) : GalleryRepository {
    override suspend fun getUserGallery(userId: String): Result<List<MediaItem>> = Result.success(items)
}
