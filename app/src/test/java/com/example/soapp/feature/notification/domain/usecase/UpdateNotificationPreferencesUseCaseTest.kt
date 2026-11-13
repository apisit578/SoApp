package com.example.soapp.feature.notification.domain.usecase

import com.example.soapp.core.model.NotificationItem
import com.example.soapp.core.model.NotificationPreference
import com.example.soapp.feature.notification.domain.repository.NotificationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateNotificationPreferencesUseCaseTest {

    @Test
    fun `invoke should update notification preferences successfully`() = runTest {
        val fakeRepo = FakeNotificationPrefRepo()
        val useCase = UpdateNotificationPreferencesUseCase(fakeRepo)
        val newPref = NotificationPreference(enableLikes = true, enableComments = false)

        val result = useCase(newPref)

        assertTrue(result.isSuccess)
        assertEquals(false, result.getOrNull()?.enableComments)
    }
}

private class FakeNotificationPrefRepo : NotificationRepository {
    private var pref = NotificationPreference()
    override suspend fun getNotifications(): Result<List<NotificationItem>> = Result.success(emptyList())
    override suspend fun markAsRead(id: String): Result<Unit> = Result.success(Unit)
    override suspend fun updatePreferences(preference: NotificationPreference): Result<NotificationPreference> {
        pref = preference
        return Result.success(pref)
    }
    override suspend fun getPreferences(): Result<NotificationPreference> = Result.success(pref)
}
