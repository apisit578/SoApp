package com.example.soapp.feature.settings.presentation

import com.example.soapp.core.theme.AppThemePreference

sealed interface SettingsUiEvent {
    data class ChangeTheme(val theme: AppThemePreference) : SettingsUiEvent
}
