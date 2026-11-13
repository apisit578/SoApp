package com.example.soapp.feature.report.domain.usecase

import com.example.soapp.core.model.ReportReason
import com.example.soapp.feature.report.domain.repository.ReportRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ReportPostUseCaseTest {

    @Test
    fun `invoke should report post successfully`() = runTest {
        val fakeRepo = FakeReportRepo()
        val useCase = ReportPostUseCase(fakeRepo)

        val result = useCase("p1", ReportReason.SPAM, "Spam content details")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }
}

private class FakeReportRepo : ReportRepository {
    override suspend fun reportPost(postId: String, reason: ReportReason, details: String): Result<Boolean> {
        return Result.success(true)
    }
}
