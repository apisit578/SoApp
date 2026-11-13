package com.example.soapp.feature.blockuser.domain.usecase

import com.example.soapp.feature.blockuser.domain.repository.BlockRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BlockUserUseCaseTest {

    @Test
    fun `invoke with blank userId should return failure`() = runTest {
        val fakeRepo = FakeBlockRepo()
        val useCase = BlockUserUseCase(fakeRepo)

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertEquals("User ID ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid userId should block user successfully`() = runTest {
        val fakeRepo = FakeBlockRepo()
        val useCase = BlockUserUseCase(fakeRepo)

        val result = useCase("u99")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }
}

private class FakeBlockRepo : BlockRepository {
    private val blocked = mutableListOf<String>()

    override suspend fun blockUser(userId: String): Result<Boolean> {
        blocked.add(userId)
        return Result.success(true)
    }

    override suspend fun unblockUser(userId: String): Result<Boolean> {
        blocked.remove(userId)
        return Result.success(true)
    }

    override suspend fun getBlockedUserIds(): Result<List<String>> = Result.success(blocked)
}
