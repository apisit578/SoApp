package com.example.soapp.feature.subscription.presentation

sealed interface SubscriptionUiEvent {
    data class SelectPlan(val planId: String) : SubscriptionUiEvent
    data object Purchase : SubscriptionUiEvent
    data object DismissSuccessMessage : SubscriptionUiEvent
}
