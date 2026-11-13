package com.example.soapp.core.locale

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppLanguage(val code: String, val displayName: String) {
    THAI("th", "ภาษาไทย (Thai) 🇹🇭"),
    ENGLISH("en", "English (US) 🇬🇧")
}

/**
 * App Language & Localization Controller.
 */
object LocaleManager {
    private val _currentLanguage = MutableStateFlow(AppLanguage.THAI)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }
}
