package com.example.soapp.feature.editpost.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class DeletePostUseCaseTest {

    @Test
    fun `invoke should delete post and return success`() = runTest {
        val fakeRepo = FakeDeletePostRepo()
        val useCase = DeletePostUseCase(fakeRepo)

        val result = useCase("p1")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }
}

private class FakeDeletePostRepo : FeedRepository {
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> = throw NotImplementedError()
    override suspend fun updatePost(postId: String, newContent: String): Result<Post> = throw NotImplementedError()
    override suspend fun deletePost(postId: String): Result<Boolean> = Result.success(true)
    override suspend fun toggleBookmarkPost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun getBookmarkedPosts(): Result<List<Post>> = Result.success(emptyList())
}
