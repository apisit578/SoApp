package com.example.soapp.feature.settings.presentation

import app.cash.turbine.test
import com.example.soapp.core.theme.AppThemePreference
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `ChangeTheme event should update current theme state`() = runTest {
        val viewModel = SettingsViewModel()

        viewModel.onEvent(SettingsUiEvent.ChangeTheme(AppThemePreference.DARK))

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(AppThemePreference.DARK, state.currentTheme)
        }
    }
}
