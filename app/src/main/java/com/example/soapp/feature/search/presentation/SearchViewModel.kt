package com.example.soapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.feed.data.datasource.MockFeedDataSourceImpl
import com.example.soapp.feature.feed.data.repository.FeedRepositoryImpl
import com.example.soapp.feature.search.domain.usecase.SearchPostsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchPostsUseCase: SearchPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    performSearch(query)
                }
        }
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.QueryChanged -> {
                _uiState.update { it.copy(query = event.query) }
                _searchQuery.value = event.query
            }
            is SearchUiEvent.ClearQuery -> {
                _uiState.update { it.copy(query = "") }
                _searchQuery.value = ""
            }
        }
    }

    private suspend fun performSearch(query: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        searchPostsUseCase(query)
            .onSuccess { posts ->
                _uiState.update { it.copy(posts = posts, isLoading = false) }
            }
            .onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message ?: "ค้นหาไม่สำเร็จ") }
            }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockFeedDataSourceImpl()
                val repository = FeedRepositoryImpl(dataSource)
                val useCase = SearchPostsUseCase(repository)
                return SearchViewModel(searchPostsUseCase = useCase) as T
            }
        }
    }
}
