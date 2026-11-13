package com.example.soapp.feature.report.presentation

import com.example.soapp.core.model.ReportReason

sealed interface ReportUiEvent {
    data class SelectReason(val reason: ReportReason) : ReportUiEvent
    data class UpdateDetails(val text: String) : ReportUiEvent
    data object Submit : ReportUiEvent
}
