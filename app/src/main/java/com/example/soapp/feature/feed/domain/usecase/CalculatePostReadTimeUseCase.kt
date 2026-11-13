package com.example.soapp.feature.feed.domain.usecase

import kotlin.math.ceil

data class ReadTimeInfo(
    val wordCount: Int,
    val readTimeMinutes: Int,
    val displayString: String
)

class CalculatePostReadTimeUseCase {

    operator fun invoke(content: String): ReadTimeInfo {
        val words = content.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }
        val wordCount = words.size
        val minutes = ceil(wordCount / 200.0).toInt().coerceAtLeast(1)
        return ReadTimeInfo(
            wordCount = wordCount,
            readTimeMinutes = minutes,
            displayString = "อ่านประมาณ $minutes นาที"
        )
    }
}
