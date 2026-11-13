package com.example.soapp.feature.report.data.repository

import com.example.soapp.core.model.ReportReason
import com.example.soapp.feature.report.data.datasource.MockReportDataSource
import com.example.soapp.feature.report.domain.repository.ReportRepository

class ReportRepositoryImpl(
    private val dataSource: MockReportDataSource
) : ReportRepository {

    override suspend fun reportPost(postId: String, reason: ReportReason, details: String): Result<Boolean> {
        return runCatching { dataSource.sendReport(postId, reason, details) }
    }
}
