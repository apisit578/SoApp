package com.example.soapp.feature.search.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchPostsUseCaseTest {

    @Test
    fun `invoke with search keyword should return matching posts`() = runTest {
        val posts = listOf(
            Post("1", "Somchai", "@somchai", null, "Kotlin Compose Clean Architecture", "1h", 0),
            Post("2", "John", "@john", null, "Hello World Flutter", "2h", 0)
        )
        val fakeRepo = FakeFeedRepo(posts)
        val useCase = SearchPostsUseCase(fakeRepo)

        val result = useCase("Kotlin")

        assertTrue(result.isSuccess)
        val searchResults = result.getOrNull()
        assertEquals(1, searchResults?.size)
        assertEquals("1", searchResults?.first()?.id)
    }
}

private class FakeFeedRepo(private val posts: List<Post>) : FeedRepository {
    override suspend fun getFeedPosts(): Result<List<Post>> = Result.success(posts)
    override suspend fun toggleLikePost(postId: String): Result<Post> = throw NotImplementedError()
    override suspend fun createPost(content: String): Result<Post> = throw NotImplementedError()
}
