package com.example.soapp.feature.subscription.data.repository

import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan
import com.example.soapp.feature.subscription.data.datasource.MockSubscriptionDataSource
import com.example.soapp.feature.subscription.domain.repository.SubscriptionRepository

class SubscriptionRepositoryImpl(
    private val dataSource: MockSubscriptionDataSource
) : SubscriptionRepository {

    override suspend fun getSubscriptionPlans(): Result<List<SubscriptionPlan>> {
        return runCatching { dataSource.getPlans() }
    }

    override suspend fun purchaseSubscription(planId: String, paymentResult: PaymentResult): Result<Boolean> {
        return runCatching { dataSource.purchase(planId, paymentResult) }
    }

    override suspend fun cancelSubscription(): Result<Boolean> {
        return runCatching { dataSource.cancelSubscription() }
    }

    override suspend fun isUserPro(): Result<Boolean> {
        return runCatching { dataSource.isProUser() }
    }
}
