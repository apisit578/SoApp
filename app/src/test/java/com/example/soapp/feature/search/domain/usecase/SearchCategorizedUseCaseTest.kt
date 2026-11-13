package com.example.soapp.feature.search.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.SearchCategory
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchCategorizedUseCaseTest {

    @Test
    fun `invoke with category ALL should return matching posts and authors`() = runTest {
        val posts = listOf(
            Post("1", "Somchai", "@somchai", null, "Kotlin Compose Clean Architecture", "1h", 0),
            Post("2", "John", "@john", null, "Hello World Flutter", "2h", 0)
        )
        val fakeRepo = FakeSearchRepo(posts)
        val useCase = SearchCategorizedUseCase(fakeRepo)

        val result = useCase(query = "Somchai", category = SearchCategory.ALL)

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
    }
}

private class FakeSearchRepo(private val posts: List<Post>) : FeedRepository {
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(posts)
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> = throw NotImplementedError()
    override suspend fun updatePost(postId: String, newContent: String): Result<Post> = throw NotImplementedError()
    override suspend fun deletePost(postId: String): Result<Boolean> = Result.success(true)
    override suspend fun toggleBookmarkPost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun getBookmarkedPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun getPostsByTopic(topic: String): Result<List<Post>> = Result.success(emptyList())
}
