package com.example.soapp.feature.subscription.domain.usecase

import com.example.soapp.core.model.PaymentResult
import com.example.soapp.feature.subscription.domain.repository.SubscriptionRepository

class PurchaseSubscriptionUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(planId: String, paymentResult: PaymentResult): Result<Boolean> {
        return repository.purchaseSubscription(planId, paymentResult)
    }
}
