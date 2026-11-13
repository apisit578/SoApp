package com.example.soapp.feature.gallery.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.MediaItem
import com.example.soapp.feature.gallery.domain.repository.GalleryRepository
import com.example.soapp.feature.gallery.domain.usecase.GetUserGalleryUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init should load user gallery successfully`() = runTest {
        val testItems = listOf(
            MediaItem("m1", "Photo 1", "http://example.com/1.jpg", "1d")
        )
        val fakeRepo = FakeGalleryRepo(testItems)
        val viewModel = GalleryViewModel(userId = "user_1", getUserGalleryUseCase = GetUserGalleryUseCase(fakeRepo))

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is GalleryUiState.Success)
            assertEquals(testItems, (state as GalleryUiState.Success).items)
        }
    }
}

private class FakeGalleryRepo(private val items: List<MediaItem>) : GalleryRepository {
    override suspend fun getUserGallery(userId: String): Result<List<MediaItem>> = Result.success(items)
}
