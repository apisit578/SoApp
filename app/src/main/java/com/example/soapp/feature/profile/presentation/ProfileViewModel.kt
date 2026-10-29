package com.example.soapp.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.profile.data.datasource.MockProfileDataSourceImpl
import com.example.soapp.feature.profile.data.repository.ProfileRepositoryImpl
import com.example.soapp.feature.profile.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState.Loading }
            getUserProfileUseCase()
                .onSuccess { data ->
                    _uiState.update {
                        ProfileUiState.Success(
                            profile = data.profile,
                            posts = data.posts
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        ProfileUiState.Error(error.message ?: "Failed to load profile")
                    }
                }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockProfileDataSourceImpl()
                val repository = ProfileRepositoryImpl(dataSource)
                val useCase = GetUserProfileUseCase(repository)
                return ProfileViewModel(getUserProfileUseCase = useCase) as T
            }
        }
    }
}
