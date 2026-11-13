package com.example.soapp.feature.feedback.domain.usecase

import com.example.soapp.core.model.FeedbackCategory
import com.example.soapp.feature.feedback.domain.repository.FeedbackRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SubmitFeedbackUseCaseTest {

    @Test
    fun `invoke with blank message should return failure`() = runTest {
        val fakeRepo = FakeFeedbackRepo()
        val useCase = SubmitFeedbackUseCase(fakeRepo)

        val result = useCase(FeedbackCategory.GENERAL, rating = 5, message = "   ")

        assertTrue(result.isFailure)
        assertEquals("โปรดกรอกข้อความเสนอแนะ", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with invalid rating should return failure`() = runTest {
        val fakeRepo = FakeFeedbackRepo()
        val useCase = SubmitFeedbackUseCase(fakeRepo)

        val result = useCase(FeedbackCategory.BUG, rating = 6, message = "มีปัญหากดปุ่มไม่ได้")

        assertTrue(result.isFailure)
        assertEquals("โปรดให้คะแนนระหว่าง 1 ถึง 5 ดาว", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid data should return success`() = runTest {
        val fakeRepo = FakeFeedbackRepo()
        val useCase = SubmitFeedbackUseCase(fakeRepo)

        val result = useCase(FeedbackCategory.FEATURE_REQUEST, rating = 5, message = "อยากได้โหมดมืดเพิ่มครับ")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }
}

private class FakeFeedbackRepo : FeedbackRepository {
    override suspend fun submitFeedback(category: FeedbackCategory, rating: Int, message: String): Result<Boolean> {
        return Result.success(true)
    }
}
