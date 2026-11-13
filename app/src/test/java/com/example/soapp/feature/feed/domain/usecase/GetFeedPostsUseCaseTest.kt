package com.example.soapp.feature.feed.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit Test for [GetFeedPostsUseCase].
 *
 * Demonstrates domain layer testing using a Fake Repository.
 * Pure JVM Unit Tests run extremely fast without requiring an Android Device/Emulator.
 */
class GetFeedPostsUseCaseTest {

    @Test
    fun `invoke should return list of posts when repository succeeds`() = runTest {
        // Arrange (Setup fake data & repository)
        val expectedPosts = listOf(
            Post(
                id = "1",
                authorName = "Test Author",
                authorHandle = "@test",
                content = "Test content",
                timeAgo = "1m",
                likeCount = 5,
                isLiked = false
            )
        )
        val fakeRepository = FakeFeedRepository(resultToReturn = Result.success(expectedPosts))
        val useCase = GetFeedPostsUseCase(fakeRepository)

        // Act (Execute UseCase)
        val result = useCase()

        // Assert (Verify results)
        assertTrue(result.isSuccess)
        assertEquals(expectedPosts, result.getOrNull())
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        // Arrange
        val expectedException = RuntimeException("Network Error")
        val fakeRepository = FakeFeedRepository(resultToReturn = Result.failure(expectedException))
        val useCase = GetFeedPostsUseCase(fakeRepository)

        // Act
        val result = useCase()

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }
}

/**
 * Fake implementation of [FeedRepository] for unit testing.
 * Fakes are preferred over Mocks for Repository interfaces in Clean Architecture.
 */
private class FakeFeedRepository(
    private val resultToReturn: Result<List<Post>>
) : FeedRepository {

    override suspend fun getFeedPosts(): Result<List<Post>> {
        return resultToReturn
    }

    override suspend fun toggleLikePost(postId: String): Result<Post> {
        throw NotImplementedError("Not needed for this test")
    }

    override suspend fun createPost(content: String): Result<Post> {
        throw NotImplementedError("Not needed for this test")
    }

    override suspend fun updatePost(postId: String, newContent: String): Result<Post> {
        throw NotImplementedError("Not needed for this test")
    }

    override suspend fun deletePost(postId: String): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun toggleBookmarkPost(postId: String): Result<Post> {
        throw NotImplementedError("Not needed for this test")
    }

    override suspend fun getBookmarkedPosts(): Result<List<Post>> {
        return Result.success(emptyList())
    }

    override suspend fun getPostsByTopic(topic: String): Result<List<Post>> {
        return Result.success(emptyList())
    }
}
