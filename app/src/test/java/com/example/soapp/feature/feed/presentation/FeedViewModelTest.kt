package com.example.soapp.feature.feed.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import com.example.soapp.feature.feed.domain.usecase.GetFeedPostsUseCase
import com.example.soapp.feature.feed.domain.usecase.ToggleLikePostUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Unit Test for [FeedViewModel].
 *
 * Demonstrates Presentation Layer testing using Coroutines, Turbine for StateFlow observation,
 * and MainDispatcherRule.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init should load posts and transition state to Success`() = runTest {
        // Arrange
        val testPosts = listOf(
            Post(id = "1", authorName = "Alice", authorHandle = "@alice", content = "Hello World", timeAgo = "1h", likeCount = 10, isLiked = false)
        )
        val fakeRepo = FakeFeedRepository(postsToReturn = testPosts)
        val getPostsUseCase = GetFeedPostsUseCase(fakeRepo)
        val toggleLikeUseCase = ToggleLikePostUseCase(fakeRepo)

        // Act & Assert using Turbine to inspect StateFlow emissions
        val viewModel = FeedViewModel(getPostsUseCase, toggleLikeUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is FeedUiState.Success)
            assertEquals(testPosts, (state as FeedUiState.Success).posts)
        }
    }

    @Test
    fun `onEvent ToggleLike should update liked state of post`() = runTest {
        // Arrange
        val initialPost = Post(id = "1", authorName = "Alice", authorHandle = "@alice", content = "Hello World", timeAgo = "1h", likeCount = 10, isLiked = false)
        val fakeRepo = FakeFeedRepository(postsToReturn = listOf(initialPost))
        val viewModel = FeedViewModel(
            getFeedPostsUseCase = GetFeedPostsUseCase(fakeRepo),
            toggleLikePostUseCase = ToggleLikePostUseCase(fakeRepo)
        )

        // Act (Simulate User Clicking Like)
        viewModel.onEvent(FeedUiEvent.ToggleLike(postId = "1"))

        // Assert
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is FeedUiState.Success)
            val updatedPost = (state as FeedUiState.Success).posts.first()
            assertTrue(updatedPost.isLiked)
            assertEquals(11, updatedPost.likeCount)
        }
    }
}

private class FakeFeedRepository(
    private val postsToReturn: List<Post>
) : FeedRepository {

    private val currentPosts = postsToReturn.toMutableList()

    override suspend fun getFeedPosts(): Result<List<Post>> {
        return Result.success(currentPosts.toList())
    }

    override suspend fun toggleLikePost(postId: String): Result<Post> {
        val index = currentPosts.indexOfFirst { it.id == postId }
        if (index != -1) {
            val old = currentPosts[index]
            val updated = old.copy(isLiked = !old.isLiked, likeCount = if (old.isLiked) old.likeCount - 1 else old.likeCount + 1)
            currentPosts[index] = updated
            return Result.success(updated)
        }
        return Result.failure(NoSuchElementException("Post not found"))
    }

    override suspend fun createPost(content: String): Result<Post> {
        val newPost = Post("p_${System.currentTimeMillis()}", "Alice", "@alice", null, content, "เมื่อครู่", 0)
        currentPosts.add(0, newPost)
        return Result.success(newPost)
    }

    override suspend fun updatePost(postId: String, newContent: String): Result<Post> {
        val index = currentPosts.indexOfFirst { it.id == postId }
        if (index != -1) {
            val updated = currentPosts[index].copy(content = newContent)
            currentPosts[index] = updated
            return Result.success(updated)
        }
        return Result.failure(NoSuchElementException())
    }

    override suspend fun deletePost(postId: String): Result<Boolean> {
        currentPosts.removeIf { it.id == postId }
        return Result.success(true)
    }

    override suspend fun toggleBookmarkPost(postId: String): Result<Post> {
        throw NotImplementedError()
    }

    override suspend fun getBookmarkedPosts(): Result<List<Post>> {
        return Result.success(emptyList())
    }
}
