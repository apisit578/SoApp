package com.example.soapp.feature.settings.presentation

import com.example.soapp.core.theme.AppThemePreference

data class SettingsUiState(
    val currentTheme: AppThemePreference = AppThemePreference.SYSTEM,
    val appVersion: String = "1.0.0 (Build 2026.1)"
)
