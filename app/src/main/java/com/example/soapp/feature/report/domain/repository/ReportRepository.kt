package com.example.soapp.feature.report.domain.repository

import com.example.soapp.core.model.ReportReason

interface ReportRepository {
    suspend fun reportPost(postId: String, reason: ReportReason, details: String): Result<Boolean>
}
