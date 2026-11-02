package com.example.soapp.feature.subscription.domain.usecase

import com.example.soapp.feature.subscription.domain.repository.SubscriptionRepository

class CancelSubscriptionUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return repository.cancelSubscription()
    }
}
