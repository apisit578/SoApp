package com.example.soapp.feature.feedback.domain.repository

import com.example.soapp.core.model.FeedbackCategory

interface FeedbackRepository {
    suspend fun submitFeedback(category: FeedbackCategory, rating: Int, message: String): Result<Boolean>
}
