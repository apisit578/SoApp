package com.example.soapp.feature.follower.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.follower.data.datasource.MockFollowDataSourceImpl
import com.example.soapp.feature.follower.data.repository.FollowRepositoryImpl
import com.example.soapp.feature.follower.domain.usecase.GetFollowersUseCase
import com.example.soapp.feature.follower.domain.usecase.GetFollowingUseCase
import com.example.soapp.feature.follower.domain.usecase.ToggleFollowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowViewModel(
    private val userId: String,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
    private val toggleFollowUseCase: ToggleFollowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FollowUiState>(FollowUiState.Loading)
    val uiState: StateFlow<FollowUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: FollowUiEvent) {
        when (event) {
            is FollowUiEvent.SelectTab -> selectTab(event.tab)
            is FollowUiEvent.ToggleFollow -> toggleFollow(event.targetUserId)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { FollowUiState.Loading }
            val followers = getFollowersUseCase(userId).getOrDefault(emptyList())
            val following = getFollowingUseCase(userId).getOrDefault(emptyList())
            _uiState.update {
                FollowUiState.Success(
                    followers = followers,
                    following = following
                )
            }
        }
    }

    private fun selectTab(tab: FollowTab) {
        _uiState.update { state ->
            if (state is FollowUiState.Success) {
                state.copy(activeTab = tab)
            } else state
        }
    }

    private fun toggleFollow(targetUserId: String) {
        viewModelScope.launch {
            toggleFollowUseCase(targetUserId)
                .onSuccess {
                    loadData()
                }
        }
    }

    companion object {
        fun provideFactory(userId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockFollowDataSourceImpl()
                val repository = FollowRepositoryImpl(dataSource)
                val getFollowers = GetFollowersUseCase(repository)
                val getFollowing = GetFollowingUseCase(repository)
                val toggleFollow = ToggleFollowUseCase(repository)
                return FollowViewModel(
                    userId = userId,
                    getFollowersUseCase = getFollowers,
                    getFollowingUseCase = getFollowing,
                    toggleFollowUseCase = toggleFollow
                ) as T
            }
        }
    }
}
