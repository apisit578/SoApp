package com.example.soapp.feature.feedback.data.repository

import com.example.soapp.core.model.FeedbackCategory
import com.example.soapp.feature.feedback.data.datasource.MockFeedbackDataSource
import com.example.soapp.feature.feedback.domain.repository.FeedbackRepository

class FeedbackRepositoryImpl(
    private val dataSource: MockFeedbackDataSource
) : FeedbackRepository {

    override suspend fun submitFeedback(category: FeedbackCategory, rating: Int, message: String): Result<Boolean> {
        return runCatching { dataSource.submitFeedback(category, rating, message) }
    }
}
