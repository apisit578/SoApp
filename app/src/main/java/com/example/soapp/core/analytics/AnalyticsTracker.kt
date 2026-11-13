package com.example.soapp.core.analytics

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AnalyticsEvent(
    val name: String,
    val params: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Analytics Event Tracker demonstrating event logging in Clean Architecture apps.
 */
object AnalyticsTracker {
    private val _events = MutableStateFlow<List<AnalyticsEvent>>(emptyList())
    val events: StateFlow<List<AnalyticsEvent>> = _events.asStateFlow()

    fun logEvent(name: String, params: Map<String, String> = emptyMap()) {
        val event = AnalyticsEvent(name, params)
        _events.value = listOf(event) + _events.value
    }

    fun clear() {
        _events.value = emptyList()
    }
}
