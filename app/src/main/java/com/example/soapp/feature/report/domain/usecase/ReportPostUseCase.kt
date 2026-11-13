package com.example.soapp.feature.report.domain.usecase

import com.example.soapp.core.model.ReportReason
import com.example.soapp.feature.report.domain.repository.ReportRepository

class ReportPostUseCase(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(postId: String, reason: ReportReason, details: String): Result<Boolean> {
        return repository.reportPost(postId, reason, details.trim())
    }
}
