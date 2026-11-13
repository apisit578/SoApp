package com.example.soapp.feature.chat.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatAudioDurationUseCaseTest {

    @Test
    fun `invoke with 45 seconds should format to 00 45`() {
        val useCase = FormatAudioDurationUseCase()

        val formatted = useCase(45)

        assertEquals("00:45", formatted)
    }

    @Test
    fun `invoke with 125 seconds should format to 02 05`() {
        val useCase = FormatAudioDurationUseCase()

        val formatted = useCase(125)

        assertEquals("02:05", formatted)
    }
}
