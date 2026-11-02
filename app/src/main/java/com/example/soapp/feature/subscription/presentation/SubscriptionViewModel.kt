package com.example.soapp.feature.subscription.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.soapp.core.model.PaymentResult
import com.example.soapp.feature.subscription.data.datasource.MockSubscriptionDataSourceImpl
import com.example.soapp.feature.subscription.data.repository.SubscriptionRepositoryImpl
import com.example.soapp.feature.subscription.domain.usecase.CancelSubscriptionUseCase
import com.example.soapp.feature.subscription.domain.usecase.GetSubscriptionPlansUseCase
import com.example.soapp.feature.subscription.domain.usecase.PurchaseSubscriptionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionViewModel(
    private val getSubscriptionPlansUseCase: GetSubscriptionPlansUseCase,
    private val purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase,
    private val cancelSubscriptionUseCase: CancelSubscriptionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SubscriptionUiState>(SubscriptionUiState.Loading)
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadPlans()
    }

    fun onEvent(event: SubscriptionUiEvent) {
        when (event) {
            is SubscriptionUiEvent.SelectPlan -> selectPlan(event.planId)
            is SubscriptionUiEvent.SelectPaymentOption -> selectPaymentOption(event.option)
            is SubscriptionUiEvent.OpenPaymentSheet -> openPaymentSheet()
            is SubscriptionUiEvent.ClosePaymentSheet -> closePaymentSheet()
            is SubscriptionUiEvent.ConfirmPayment -> confirmPayment()
            is SubscriptionUiEvent.CancelActiveSubscription -> cancelActiveSubscription()
            is SubscriptionUiEvent.DismissSuccessMessage -> dismissSuccessMessage()
            is SubscriptionUiEvent.DismissErrorMessage -> dismissErrorMessage()
        }
    }

    private fun loadPlans() {
        viewModelScope.launch {
            _uiState.update { SubscriptionUiState.Loading }
            getSubscriptionPlansUseCase()
                .onSuccess { plans ->
                    val defaultPlanId = plans.firstOrNull { it.isBestValue }?.id ?: plans.firstOrNull()?.id ?: ""
                    val isPro = com.example.soapp.core.session.UserSessionManager.isPro.value
                    _uiState.update {
                        SubscriptionUiState.Content(
                            plans = plans,
                            selectedPlanId = defaultPlanId,
                            isPro = isPro
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

    private fun selectPaymentOption(option: PaymentResult) {
        _uiState.update { state ->
            if (state is SubscriptionUiState.Content) {
                state.copy(selectedPaymentOption = option)
            } else state
        }
    }

    private fun openPaymentSheet() {
        _uiState.update { state ->
            if (state is SubscriptionUiState.Content) {
                state.copy(showPaymentSheet = true, errorMessage = null)
            } else state
        }
    }

    private fun closePaymentSheet() {
        _uiState.update { state ->
            if (state is SubscriptionUiState.Content) {
                state.copy(showPaymentSheet = false)
            } else state
        }
    }

    private fun confirmPayment() {
        val currentState = _uiState.value
        if (currentState is SubscriptionUiState.Content && !currentState.isPurchasing) {
            viewModelScope.launch {
                _uiState.update { (it as SubscriptionUiState.Content).copy(isPurchasing = true) }
                purchaseSubscriptionUseCase(currentState.selectedPlanId, currentState.selectedPaymentOption)
                    .onSuccess { purchased ->
                        _uiState.update { state ->
                            if (state is SubscriptionUiState.Content) {
                                if (purchased) {
                                    state.copy(
                                        isPro = true,
                                        isPurchasing = false,
                                        showPaymentSheet = false,
                                        purchaseSuccessMessage = "ยินดีต้อนรับสู่ SoApp Pro! สมาชิกของคุณเปิดใช้งานแล้ว 🎉"
                                    )
                                } else {
                                    // User cancelled
                                    state.copy(
                                        isPurchasing = false,
                                        showPaymentSheet = false
                                    )
                                }
                            } else state
                        }
                    }
                    .onFailure { error ->
                        _uiState.update { state ->
                            if (state is SubscriptionUiState.Content) {
                                state.copy(
                                    isPurchasing = false,
                                    showPaymentSheet = false,
                                    errorMessage = error.message ?: "เกิดข้อผิดพลาดในการชำระเงิน"
                                )
                            } else state
                        }
                    }
            }
        }
    }

    private fun cancelActiveSubscription() {
        val currentState = _uiState.value
        if (currentState is SubscriptionUiState.Content && !currentState.isCancelling) {
            viewModelScope.launch {
                _uiState.update { (it as SubscriptionUiState.Content).copy(isCancelling = true) }
                cancelSubscriptionUseCase()
                    .onSuccess {
                        _uiState.update { state ->
                            if (state is SubscriptionUiState.Content) {
                                state.copy(
                                    isPro = false,
                                    isCancelling = false,
                                    purchaseSuccessMessage = "ยกเลิกการเป็นสมาชิก Pro เรียบร้อยแล้ว บัญชีของคุณกลับสู่แพ็กเกจฟรี"
                                )
                            } else state
                        }
                    }
                    .onFailure { error ->
                        _uiState.update { state ->
                            if (state is SubscriptionUiState.Content) {
                                state.copy(
                                    isCancelling = false,
                                    errorMessage = error.message ?: "ไม่สามารถยกเลิกสมาชิกได้"
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

    private fun dismissErrorMessage() {
        _uiState.update { state ->
            if (state is SubscriptionUiState.Content) {
                state.copy(errorMessage = null)
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
                val cancelUseCase = CancelSubscriptionUseCase(repository)
                return SubscriptionViewModel(
                    getSubscriptionPlansUseCase = getPlansUseCase,
                    purchaseSubscriptionUseCase = purchaseUseCase,
                    cancelSubscriptionUseCase = cancelUseCase
                ) as T
            }
        }
    }
}
