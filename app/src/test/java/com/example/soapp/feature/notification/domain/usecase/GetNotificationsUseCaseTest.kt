package com.example.soapp.feature.notification.domain.usecase

import com.example.soapp.core.model.NotificationItem
import com.example.soapp.core.model.NotificationPreference
import com.example.soapp.core.model.NotificationType
import com.example.soapp.feature.notification.domain.repository.NotificationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetNotificationsUseCaseTest {

    @Test
    fun `invoke should return list of notifications`() = runTest {
        val expected = listOf(
            NotificationItem("n1", NotificationType.LIKE, "Alice", "Liked your post", "1m", false)
        )
        val fakeRepo = FakeNotificationRepo(expected)
        val useCase = GetNotificationsUseCase(fakeRepo)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }
}

private class FakeNotificationRepo(private val items: List<NotificationItem>) : NotificationRepository {
    override suspend fun getNotifications(): Result<List<NotificationItem>> = Result.success(items)
    override suspend fun markAsRead(id: String): Result<Unit> = Result.success(Unit)
    override suspend fun updatePreferences(preference: NotificationPreference): Result<NotificationPreference> = Result.success(preference)
    override suspend fun getPreferences(): Result<NotificationPreference> = Result.success(NotificationPreference())
}
