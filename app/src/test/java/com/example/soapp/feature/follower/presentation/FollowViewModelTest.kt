package com.example.soapp.feature.follower.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.FollowUser
import com.example.soapp.feature.follower.domain.repository.FollowRepository
import com.example.soapp.feature.follower.domain.usecase.GetFollowersUseCase
import com.example.soapp.feature.follower.domain.usecase.GetFollowingUseCase
import com.example.soapp.feature.follower.domain.usecase.ToggleFollowUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FollowViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init should load followers and following list successfully`() = runTest {
        val followers = listOf(FollowUser("u1", "Alice", "@alice", "Bio", null, true))
        val fakeRepo = FakeFollowRepo(followers)
        val viewModel = FollowViewModel(
            userId = "user_1",
            getFollowersUseCase = GetFollowersUseCase(fakeRepo),
            getFollowingUseCase = GetFollowingUseCase(fakeRepo),
            toggleFollowUseCase = ToggleFollowUseCase(fakeRepo)
        )

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is FollowUiState.Success)
            assertEquals(followers, (state as FollowUiState.Success).followers)
        }
    }
}

private class FakeFollowRepo(private val followers: List<FollowUser>) : FollowRepository {
    override suspend fun getFollowers(userId: String): Result<List<FollowUser>> = Result.success(followers)

    override suspend fun getFollowing(userId: String): Result<List<FollowUser>> = Result.success(emptyList())

    override suspend fun toggleFollow(targetUserId: String): Result<FollowUser> {
        return Result.success(FollowUser(targetUserId, "User", "@handle", "Bio", null, true))
    }
}
