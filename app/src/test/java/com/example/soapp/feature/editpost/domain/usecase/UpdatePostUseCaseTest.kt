package com.example.soapp.feature.editpost.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdatePostUseCaseTest {

    @Test
    fun `invoke with blank content should return failure`() = runTest {
        val fakeRepo = FakeEditPostRepo()
        val useCase = UpdatePostUseCase(fakeRepo)

        val result = useCase("p1", "   ")

        assertTrue(result.isFailure)
        assertEquals("เนื้อหาแก้ไขต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid content should update post`() = runTest {
        val fakeRepo = FakeEditPostRepo()
        val useCase = UpdatePostUseCase(fakeRepo)

        val result = useCase("p1", "ข้อความใหม่ที่แก้ไขแล้ว")

        assertTrue(result.isSuccess)
        assertEquals("ข้อความใหม่ที่แก้ไขแล้ว", result.getOrNull()?.content)
    }
}

private class FakeEditPostRepo : FeedRepository {
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> = throw NotImplementedError()
    override suspend fun updatePost(postId: String, newContent: String): Result<Post> {
        return Result.success(Post(postId, "Author", "@handle", null, newContent, "1m", 0))
    }
    override suspend fun deletePost(postId: String): Result<Boolean> = Result.success(true)
    override suspend fun toggleBookmarkPost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun getBookmarkedPosts(): Result<List<Post>> = Result.success(emptyList())
}
