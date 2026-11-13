package com.example.soapp.feature.comment.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.Comment
import com.example.soapp.feature.comment.domain.repository.CommentRepository
import com.example.soapp.feature.comment.domain.usecase.AddCommentUseCase
import com.example.soapp.feature.comment.domain.usecase.GetCommentsUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommentViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `SubmitComment with valid text should add new comment to list`() = runTest {
        val fakeRepo = FakeCommentRepository()
        val viewModel = CommentViewModel(
            postId = "1",
            getCommentsUseCase = GetCommentsUseCase(fakeRepo),
            addCommentUseCase = AddCommentUseCase(fakeRepo)
        )

        // Enter input
        viewModel.onEvent(CommentUiEvent.UpdateInput("ความคิดเห็นทดสอบ"))
        // Submit
        viewModel.onEvent(CommentUiEvent.SubmitComment)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is CommentUiState.Success)
            val successState = state as CommentUiState.Success
            assertEquals(1, successState.comments.size)
            assertEquals("ความคิดเห็นทดสอบ", successState.comments.first().text)
            assertEquals("", successState.currentInputText)
        }
    }
}

private class FakeCommentRepository : CommentRepository {
    private val comments = mutableListOf<Comment>()

    override suspend fun getComments(postId: String): Result<List<Comment>> {
        return Result.success(comments.toList())
    }

    override suspend fun addComment(postId: String, text: String): Result<Comment> {
        val newComment = Comment("c1", postId, "Test User", "@test", null, text, "เมื่อครู่")
        comments.add(0, newComment)
        return Result.success(newComment)
    }
}
