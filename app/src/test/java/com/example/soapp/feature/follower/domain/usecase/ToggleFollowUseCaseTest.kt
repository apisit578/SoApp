package com.example.soapp.feature.follower.domain.usecase

import com.example.soapp.core.model.FollowUser
import com.example.soapp.feature.follower.domain.repository.FollowRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ToggleFollowUseCaseTest {

    @Test
    fun `invoke should toggle isFollowing status of user`() = runTest {
        val fakeRepo = FakeFollowRepo()
        val useCase = ToggleFollowUseCase(fakeRepo)

        val result = useCase("u1")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isFollowing == true)
    }
}

private class FakeFollowRepo : FollowRepository {
    private var isFollowing = false

    override suspend fun getFollowers(userId: String): Result<List<FollowUser>> = Result.success(emptyList())

    override suspend fun getFollowing(userId: String): Result<List<FollowUser>> = Result.success(emptyList())

    override suspend fun toggleFollow(targetUserId: String): Result<FollowUser> {
        isFollowing = !isFollowing
        return Result.success(FollowUser(targetUserId, "User", "@handle", "Bio", null, isFollowing))
    }
}
