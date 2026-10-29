package com.example.soapp.feature.subscription.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.feature.subscription.data.datasource.MockSubscriptionDataSourceImpl
import com.example.soapp.feature.subscription.data.repository.SubscriptionRepositoryImpl
import com.example.soapp.feature.subscription.domain.usecase.GetSubscriptionPlansUseCase
import com.example.soapp.feature.subscription.domain.usecase.PurchaseSubscriptionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionViewModel(
    private val getSubscriptionPlansUseCase: GetSubscriptionPlansUseCase,
    private val purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SubscriptionUiState>(SubscriptionUiState.Loading)
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadPlans()
    }

    fun onEvent(event: SubscriptionUiEvent) {
        when (event) {
            is SubscriptionUiEvent.SelectPlan -> selectPlan(event.planId)
            is SubscriptionUiEvent.Purchase -> purchase()
            is SubscriptionUiEvent.DismissSuccessMessage -> dismissSuccessMessage()
        }
    }

    private fun loadPlans() {
        viewModelScope.launch {
            _uiState.update { SubscriptionUiState.Loading }
            getSubscriptionPlansUseCase()
                .onSuccess { plans ->
                    val defaultPlanId = plans.firstOrNull { it.isBestValue }?.id ?: plans.firstOrNull()?.id ?: ""
                    _uiState.update {
                        SubscriptionUiState.Content(
                            plans = plans,
                            selectedPlanId = defaultPlanId
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        SubscriptionUiState.Error(error.message ?: "Failed to load plans")
                    }
                }
        }
    }

    private fun selectPlan(planId: String) {
        _uiState.update { state ->
            if (state is SubscriptionUiState.Content) {
                state.copy(selectedPlanId = planId)
            } else state
        }
    }

    private fun purchase() {
        val currentState = _uiState.value
        if (currentState is SubscriptionUiState.Content && !currentState.isPurchasing) {
            viewModelScope.launch {
                _uiState.update { (it as SubscriptionUiState.Content).copy(isPurchasing = true) }
                purchaseSubscriptionUseCase(currentState.selectedPlanId)
                    .onSuccess {
                        _uiState.update { state ->
                            if (state is SubscriptionUiState.Content) {
                                state.copy(
                                    isPro = true,
                                    isPurchasing = false,
                                    purchaseSuccessMessage = "ยินดีต้อนรับสู่ SoApp Pro! สมาชิกของคุณเปิดใช้งานแล้ว 🎉"
                                )
                            } else state
                        }
                    }
                    .onFailure { error ->
                        _uiState.update { state ->
                            if (state is SubscriptionUiState.Content) {
                                state.copy(
                                    isPurchasing = false,
                                    errorMessage = error.message ?: "เกิดข้อผิดพลาดในการสมัครสมาชิก"
                                )
                            } else state
                        }
                    }
            }
        }
    }

    private fun dismissSuccessMessage() {
        _uiState.update { state ->
            if (state is SubscriptionUiState.Content) {
                state.copy(purchaseSuccessMessage = null)
            } else state
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSource = MockSubscriptionDataSourceImpl()
                val repository = SubscriptionRepositoryImpl(dataSource)
                val getPlansUseCase = GetSubscriptionPlansUseCase(repository)
                val purchaseUseCase = PurchaseSubscriptionUseCase(repository)
                return SubscriptionViewModel(
                    getSubscriptionPlansUseCase = getPlansUseCase,
                    purchaseSubscriptionUseCase = purchaseUseCase
                ) as T
            }
        }
    }
}
