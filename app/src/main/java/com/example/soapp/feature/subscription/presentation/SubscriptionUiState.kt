package com.example.soapp.feature.subscription.presentation

import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan

sealed interface SubscriptionUiState {
    data object Loading : SubscriptionUiState
    data class Content(
        val plans: List<SubscriptionPlan>,
        val selectedPlanId: String,
        val isPro: Boolean = false,
        val showPaymentSheet: Boolean = false,
        val selectedPaymentOption: PaymentResult = PaymentResult.SUCCESS,
        val isPurchasing: Boolean = false,
        val isCancelling: Boolean = false,
        val purchaseSuccessMessage: String? = null,
        val errorMessage: String? = null
    ) : SubscriptionUiState
    data class Error(val message: String) : SubscriptionUiState
}
