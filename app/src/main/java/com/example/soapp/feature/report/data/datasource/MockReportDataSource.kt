package com.example.soapp.feature.report.data.datasource

import com.example.soapp.core.model.ReportReason
import kotlinx.coroutines.delay

interface MockReportDataSource {
    suspend fun sendReport(postId: String, reason: ReportReason, details: String): Boolean
}

class MockReportDataSourceImpl : MockReportDataSource {
    override suspend fun sendReport(postId: String, reason: ReportReason, details: String): Boolean {
        delay(400)
        return true
    }
}
