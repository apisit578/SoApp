package com.example.soapp.core.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppThemePreference(val title: String) {
    SYSTEM("ตามการตั้งค่าของระบบ"),
    LIGHT("โหมดสว่าง (Light Theme)"),
    DARK("โหมดมืด (Dark Theme)")
}

/**
 * Shared App Theme Manager for dynamic theme toggling in Jetpack Compose.
 */
object ThemeManager {
    private val _themePreference = MutableStateFlow(AppThemePreference.SYSTEM)
    val themePreference: StateFlow<AppThemePreference> = _themePreference.asStateFlow()

    fun setThemePreference(preference: AppThemePreference) {
        _themePreference.value = preference
    }
}
