package com.example.soapp.feature.feedback.domain.usecase

import com.example.soapp.core.model.FeedbackCategory
import com.example.soapp.feature.feedback.domain.repository.FeedbackRepository

class SubmitFeedbackUseCase(
    private val repository: FeedbackRepository
) {
    suspend operator fun invoke(category: FeedbackCategory, rating: Int, message: String): Result<Boolean> {
        val trimmed = message.trim()
        if (rating !in 1..5) {
            return Result.failure(IllegalArgumentException("โปรดให้คะแนนระหว่าง 1 ถึง 5 ดาว"))
        }
        if (trimmed.isEmpty()) {
            return Result.failure(IllegalArgumentException("โปรดกรอกข้อความเสนอแนะ"))
        }
        return repository.submitFeedback(category, rating, trimmed)
    }
}
