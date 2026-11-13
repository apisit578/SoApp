package com.example.soapp.feature.comment.domain.usecase

import com.example.soapp.core.model.Comment
import com.example.soapp.feature.comment.domain.repository.CommentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit Test for [AddCommentUseCase].
 * Validates blank comment checks and repository interaction.
 */
class AddCommentUseCaseTest {

    @Test
    fun `invoke with blank comment text should return failure`() = runTest {
        val fakeRepo = FakeCommentRepository()
        val useCase = AddCommentUseCase(fakeRepo)

        val result = useCase(postId = "1", text = "   ")

        assertTrue(result.isFailure)
        assertEquals("ความคิดเห็นต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid text should add comment successfully`() = runTest {
        val fakeRepo = FakeCommentRepository()
        val useCase = AddCommentUseCase(fakeRepo)

        val result = useCase(postId = "1", text = "เยี่ยมมากครับ")

        assertTrue(result.isSuccess)
        assertEquals("เยี่ยมมากครับ", result.getOrNull()?.text)
    }
}

private class FakeCommentRepository : CommentRepository {
    private val comments = mutableListOf<Comment>()

    override suspend fun getComments(postId: String): Result<List<Comment>> {
        return Result.success(comments)
    }

    override suspend fun addComment(postId: String, text: String): Result<Comment> {
        val newComment = Comment("c1", postId, "Test User", "@test", null, text, "เมื่อครู่")
        comments.add(0, newComment)
        return Result.success(newComment)
    }
}
