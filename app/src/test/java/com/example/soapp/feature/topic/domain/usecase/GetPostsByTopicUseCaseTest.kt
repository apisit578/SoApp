package com.example.soapp.feature.topic.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetPostsByTopicUseCaseTest {

    @Test
    fun `invoke with blank topic should return failure`() = runTest {
        val fakeRepo = FakeTopicRepo()
        val useCase = GetPostsByTopicUseCase(fakeRepo)

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertEquals("Topic ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid topic should return filtered posts`() = runTest {
        val fakeRepo = FakeTopicRepo()
        val useCase = GetPostsByTopicUseCase(fakeRepo)

        val result = useCase("Compose")

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
    }
}

private class FakeTopicRepo : FeedRepository {
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> = throw NotImplementedError()
    override suspend fun updatePost(postId: String, newContent: String): Result<Post> = throw NotImplementedError()
    override suspend fun deletePost(postId: String): Result<Boolean> = Result.success(true)
    override suspend fun toggleBookmarkPost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun getBookmarkedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun getPostsByTopic(topic: String): Result<List<Post>> {
        return Result.success(listOf(Post("1", "Author", "@handle", null, "#Compose post", "1h", 0)))
    }
}
