package com.example.soapp.feature.subscription.domain.usecase

import com.example.soapp.core.model.SubscriptionPlan
import com.example.soapp.feature.subscription.domain.repository.SubscriptionRepository

class GetSubscriptionPlansUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Result<List<SubscriptionPlan>> {
        return repository.getSubscriptionPlans()
    }
}
