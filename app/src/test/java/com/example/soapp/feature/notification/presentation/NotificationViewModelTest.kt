package com.example.soapp.feature.notification.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.NotificationItem
import com.example.soapp.core.model.NotificationType
import com.example.soapp.feature.notification.domain.repository.NotificationRepository
import com.example.soapp.feature.notification.domain.usecase.GetNotificationsUseCase
import com.example.soapp.feature.notification.domain.usecase.MarkNotificationAsReadUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init should load notifications successfully`() = runTest {
        val testItems = listOf(
            NotificationItem("n1", NotificationType.LIKE, "Alice", "Liked your post", "1m", false)
        )
        val fakeRepo = FakeNotificationRepo(testItems)
        val viewModel = NotificationViewModel(
            getNotificationsUseCase = GetNotificationsUseCase(fakeRepo),
            markNotificationAsReadUseCase = MarkNotificationAsReadUseCase(fakeRepo)
        )

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is NotificationUiState.Success)
            assertEquals(testItems, (state as NotificationUiState.Success).notifications)
        }
    }
}

private class FakeNotificationRepo(private val items: List<NotificationItem>) : NotificationRepository {
    override suspend fun getNotifications(): Result<List<NotificationItem>> = Result.success(items)
    override suspend fun markAsRead(id: String): Result<Unit> = Result.success(Unit)
}
