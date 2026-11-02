package com.example.soapp.feature.subscription.domain.repository

import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan

interface SubscriptionRepository {
    suspend fun getSubscriptionPlans(): Result<List<SubscriptionPlan>>
    suspend fun purchaseSubscription(planId: String, paymentResult: PaymentResult): Result<Boolean>
    suspend fun cancelSubscription(): Result<Boolean>
    suspend fun isUserPro(): Result<Boolean>
}
