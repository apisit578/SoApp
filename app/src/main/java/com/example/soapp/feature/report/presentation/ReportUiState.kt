package com.example.soapp.feature.report.presentation

import com.example.soapp.core.model.ReportReason

data class ReportUiState(
    val reason: ReportReason = ReportReason.SPAM,
    val details: String = "",
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
