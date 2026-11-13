package com.example.soapp.feature.feedback.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.FeedbackCategory
import com.example.soapp.feature.feedback.domain.repository.FeedbackRepository
import com.example.soapp.feature.feedback.domain.usecase.SubmitFeedbackUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedbackViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Submit with valid data should update state to isSuccess`() = runTest {
        val fakeRepo = FakeFeedbackRepo()
        val viewModel = FeedbackViewModel(submitFeedbackUseCase = SubmitFeedbackUseCase(fakeRepo))

        viewModel.onEvent(FeedbackUiEvent.UpdateMessage("แอปทำงานได้ดีมากครับ"))
        viewModel.onEvent(FeedbackUiEvent.Submit)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isSuccess)
        }
    }
}

private class FakeFeedbackRepo : FeedbackRepository {
    override suspend fun submitFeedback(category: FeedbackCategory, rating: Int, message: String): Result<Boolean> {
        return Result.success(true)
    }
}
