package com.example.soapp.feature.feed.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatePostReadTimeUseCaseTest {

    @Test
    fun `invoke with short post content should return 1 minute read time`() {
        val useCase = CalculatePostReadTimeUseCase()
        val content = "Hello World Clean Architecture"

        val info = useCase(content)

        assertEquals(4, info.wordCount)
        assertEquals(1, info.readTimeMinutes)
        assertEquals("อ่านประมาณ 1 นาที", info.displayString)
    }
}
