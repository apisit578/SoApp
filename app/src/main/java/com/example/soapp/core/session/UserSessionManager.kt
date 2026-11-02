package com.example.soapp.core.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Shared user session state for sandbox testing.
 */
object UserSessionManager {
    private val _isPro = MutableStateFlow(false)
    val isPro: StateFlow<Boolean> = _isPro.asStateFlow()

    fun setProStatus(isProStatus: Boolean) {
        _isPro.value = isProStatus
    }
}
