package com.example.soapp.feature.feedback.data.datasource

import com.example.soapp.core.model.FeedbackCategory
import com.example.soapp.core.model.FeedbackItem
import kotlinx.coroutines.delay

interface MockFeedbackDataSource {
    suspend fun submitFeedback(category: FeedbackCategory, rating: Int, message: String): Boolean
}

class MockFeedbackDataSourceImpl : MockFeedbackDataSource {

    private val feedbackList = mutableListOf<FeedbackItem>()

    override suspend fun submitFeedback(category: FeedbackCategory, rating: Int, message: String): Boolean {
        delay(500)
        feedbackList.add(
            FeedbackItem(
                id = "fb_${System.currentTimeMillis()}",
                category = category,
                rating = rating,
                message = message
            )
        )
        return true
    }
}
