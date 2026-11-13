package com.example.soapp.feature.bookmark.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ToggleBookmarkUseCaseTest {

    @Test
    fun `invoke should toggle bookmarked state of post`() = runTest {
        val fakeRepo = FakeBookmarkRepo()
        val useCase = ToggleBookmarkUseCase(fakeRepo)

        val result = useCase("1")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isBookmarked == true)
    }
}

private class FakeBookmarkRepo : FeedRepository {
    private var isBookmarked = false
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> = throw NotImplementedError()
    override suspend fun updatePost(postId: String, newContent: String): Result<Post> = throw NotImplementedError()
    override suspend fun deletePost(postId: String): Result<Boolean> = Result.success(true)
    override suspend fun toggleBookmarkPost(postId: String): Result<Post> {
        isBookmarked = !isBookmarked
        return Result.success(Post("1", "Author", "@handle", null, "Content", "1h", 0, false, 0, isBookmarked))
    }
    override suspend fun getBookmarkedPosts(): Result<List<Post>> = Result.success(emptyList())
}
