package com.example.soapp.feature.security.domain.usecase

import com.example.soapp.core.model.ActiveSession
import com.example.soapp.feature.security.domain.repository.SecurityRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RevokeSessionUseCaseTest {

    @Test
    fun `invoke with blank sessionId should return failure`() = runTest {
        val fakeRepo = FakeSecurityRepo()
        val useCase = RevokeSessionUseCase(fakeRepo)

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertEquals("Session ID ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid sessionId should revoke session successfully`() = runTest {
        val fakeRepo = FakeSecurityRepo()
        val useCase = RevokeSessionUseCase(fakeRepo)

        val result = useCase("s2")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }
}

private class FakeSecurityRepo : SecurityRepository {
    override suspend fun getActiveSessions(): Result<List<ActiveSession>> = Result.success(emptyList())

    override suspend fun revokeSession(sessionId: String): Result<Boolean> = Result.success(true)
}
