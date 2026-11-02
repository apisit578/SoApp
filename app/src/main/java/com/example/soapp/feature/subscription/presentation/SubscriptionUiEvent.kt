package com.example.soapp.feature.subscription.presentation

import com.example.soapp.core.model.PaymentResult

sealed interface SubscriptionUiEvent {
    data class SelectPlan(val planId: String) : SubscriptionUiEvent
    data class SelectPaymentOption(val option: PaymentResult) : SubscriptionUiEvent
    data object OpenPaymentSheet : SubscriptionUiEvent
    data object ClosePaymentSheet : SubscriptionUiEvent
    data object ConfirmPayment : SubscriptionUiEvent
    data object CancelActiveSubscription : SubscriptionUiEvent
    data object DismissSuccessMessage : SubscriptionUiEvent
    data object DismissErrorMessage : SubscriptionUiEvent
}
