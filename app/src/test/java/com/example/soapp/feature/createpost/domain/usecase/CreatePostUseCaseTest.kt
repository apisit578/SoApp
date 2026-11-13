package com.example.soapp.feature.createpost.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CreatePostUseCaseTest {

    @Test
    fun `invoke with empty content should return failure`() = runTest {
        val fakeRepo = FakeFeedRepo()
        val useCase = CreatePostUseCase(fakeRepo)

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertEquals("เนื้อหาโพสต์ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid content should create post successfully`() = runTest {
        val fakeRepo = FakeFeedRepo()
        val useCase = CreatePostUseCase(fakeRepo)

        val result = useCase("ข้อความโพสต์ใหม่")

        assertTrue(result.isSuccess)
        assertEquals("ข้อความโพสต์ใหม่", result.getOrNull()?.content)
    }
}

private class FakeFeedRepo : FeedRepository {
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> {
        val newPost = Post("p1", "Somchai Dev", "@somchai_dev", null, content, "เมื่อครู่", 0, false, 0)
        return Result.success(newPost)
    }
    override suspend fun updatePost(postId: String, newContent: String): Result<Post> = throw NotImplementedError()
    override suspend fun deletePost(postId: String): Result<Boolean> = Result.success(true)
    override suspend fun toggleBookmarkPost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun getBookmarkedPosts(): Result<List<Post>> = Result.success(emptyList())
}
