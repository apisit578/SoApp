package com.example.soapp.feature.chat.domain.usecase

import java.util.Locale

class FormatAudioDurationUseCase {

    operator fun invoke(durationSeconds: Int): String {
        if (durationSeconds <= 0) return "00:00"
        val minutes = durationSeconds / 60
        val seconds = durationSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}
