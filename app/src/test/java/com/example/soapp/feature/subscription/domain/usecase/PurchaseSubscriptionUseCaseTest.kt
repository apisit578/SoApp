package com.example.soapp.feature.subscription.domain.usecase

import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan
import com.example.soapp.feature.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit Test for [PurchaseSubscriptionUseCase].
 * Tests Sandbox payment scenario results.
 */
class PurchaseSubscriptionUseCaseTest {

    @Test
    fun `invoke with SUCCESS payment result should return true`() = runTest {
        val fakeRepo = FakeSubscriptionRepository()
        val useCase = PurchaseSubscriptionUseCase(fakeRepo)

        val result = useCase(planId = "soapp_pro_monthly", paymentResult = PaymentResult.SUCCESS)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
    }

    @Test
    fun `invoke with DECLINED payment result should return failure`() = runTest {
        val fakeRepo = FakeSubscriptionRepository()
        val useCase = PurchaseSubscriptionUseCase(fakeRepo)

        val result = useCase(planId = "soapp_pro_monthly", paymentResult = PaymentResult.DECLINED)

        assertTrue(result.isFailure)
        assertEquals("Declined", result.exceptionOrNull()?.message)
    }
}

private class FakeSubscriptionRepository : SubscriptionRepository {
    override suspend fun getSubscriptionPlans(): Result<List<SubscriptionPlan>> = Result.success(emptyList())

    override suspend fun purchaseSubscription(planId: String, paymentResult: PaymentResult): Result<Boolean> {
        return when (paymentResult) {
            PaymentResult.SUCCESS -> Result.success(true)
            PaymentResult.DECLINED -> Result.failure(IllegalStateException("Declined"))
            PaymentResult.CANCELLED -> Result.success(false)
        }
    }

    override suspend fun cancelSubscription(): Result<Boolean> = Result.success(true)

    override suspend fun isUserPro(): Result<Boolean> = Result.success(false)
}
