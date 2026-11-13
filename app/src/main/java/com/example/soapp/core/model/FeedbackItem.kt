package com.example.soapp.core.model

enum class FeedbackCategory(val title: String) {
    BUG("รายงานปัญหาการใช้งาน (Bug)"),
    FEATURE_REQUEST("เสนอแนะฟีเจอร์ใหม่"),
    GENERAL("ความพึงพอใจทั่วไป")
}

/**
 * Domain model representing user feedback.
 */
data class FeedbackItem(
    val id: String,
    val category: FeedbackCategory,
    val rating: Int,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
