package com.example.soapp.feature.presence.domain.usecase

import com.example.soapp.core.model.PresenceStatus
import com.example.soapp.core.model.UserPresence
import com.example.soapp.feature.presence.domain.repository.PresenceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateUserPresenceUseCaseTest {

    @Test
    fun `invoke with blank userId should return failure`() = runTest {
        val fakeRepo = FakePresenceRepo()
        val useCase = UpdateUserPresenceUseCase(fakeRepo)

        val result = useCase("   ", PresenceStatus.ONLINE)

        assertTrue(result.isFailure)
        assertEquals("User ID ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid data should update presence`() = runTest {
        val fakeRepo = FakePresenceRepo()
        val useCase = UpdateUserPresenceUseCase(fakeRepo)

        val result = useCase("user_1", PresenceStatus.BUSY, "ประชุมอยู่")

        assertTrue(result.isSuccess)
        assertEquals(PresenceStatus.BUSY, result.getOrNull()?.status)
        assertEquals("ประชุมอยู่", result.getOrNull()?.customStatusText)
    }
}

private class FakePresenceRepo : PresenceRepository {
    override suspend fun updatePresence(userId: String, status: PresenceStatus, customText: String?): Result<UserPresence> {
        return Result.success(UserPresence(userId, status, customText))
    }

    override suspend fun getPresence(userId: String): Result<UserPresence> {
        return Result.success(UserPresence(userId, PresenceStatus.ONLINE))
    }
}
