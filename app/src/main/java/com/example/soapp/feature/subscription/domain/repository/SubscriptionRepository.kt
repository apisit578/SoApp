package com.example.soapp.feature.subscription.domain.repository

import com.example.soapp.core.model.SubscriptionPlan

interface SubscriptionRepository {
    suspend fun getSubscriptionPlans(): Result<List<SubscriptionPlan>>
    suspend fun purchaseSubscription(planId: String): Result<Boolean>
    suspend fun isUserPro(): Result<Boolean>
}
