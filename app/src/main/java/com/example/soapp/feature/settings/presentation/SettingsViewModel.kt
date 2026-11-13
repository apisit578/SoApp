package com.example.soapp.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soapp.core.theme.AppThemePreference
import com.example.soapp.core.theme.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ThemeManager.themePreference.collect { theme ->
                _uiState.update { it.copy(currentTheme = theme) }
            }
        }
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.ChangeTheme -> changeTheme(event.theme)
        }
    }

    private fun changeTheme(theme: AppThemePreference) {
        ThemeManager.setThemePreference(theme)
    }
}
