package com.example.soapp.feature.search.presentation

sealed interface SearchUiEvent {
    data class QueryChanged(val query: String) : SearchUiEvent
    data object ClearQuery : SearchUiEvent
}
