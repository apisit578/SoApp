package com.example.soapp.feature.subscription.presentation

import app.cash.turbine.test
import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan
import com.example.soapp.feature.subscription.domain.repository.SubscriptionRepository
import com.example.soapp.feature.subscription.domain.usecase.CancelSubscriptionUseCase
import com.example.soapp.feature.subscription.domain.usecase.GetSubscriptionPlansUseCase
import com.example.soapp.feature.subscription.domain.usecase.PurchaseSubscriptionUseCase
import com.example.soapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SubscriptionViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `ConfirmPayment with SUCCESS should update isPro to true`() = runTest {
        val fakeRepo = FakeSubscriptionRepo()
        val viewModel = SubscriptionViewModel(
            getSubscriptionPlansUseCase = GetSubscriptionPlansUseCase(fakeRepo),
            purchaseSubscriptionUseCase = PurchaseSubscriptionUseCase(fakeRepo),
            cancelSubscriptionUseCase = CancelSubscriptionUseCase(fakeRepo)
        )

        viewModel.onEvent(SubscriptionUiEvent.ConfirmPayment)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is SubscriptionUiState.Content)
            val content = state as SubscriptionUiState.Content
            assertTrue(content.isPro)
            assertNotNull(content.purchaseSuccessMessage)
        }
    }

    @Test
    fun `CancelActiveSubscription should revert isPro to false`() = runTest {
        val fakeRepo = FakeSubscriptionRepo(initialPro = true)
        val viewModel = SubscriptionViewModel(
            getSubscriptionPlansUseCase = GetSubscriptionPlansUseCase(fakeRepo),
            purchaseSubscriptionUseCase = PurchaseSubscriptionUseCase(fakeRepo),
            cancelSubscriptionUseCase = CancelSubscriptionUseCase(fakeRepo)
        )

        viewModel.onEvent(SubscriptionUiEvent.CancelActiveSubscription)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is SubscriptionUiState.Content)
            val content = state as SubscriptionUiState.Content
            assertEquals(false, content.isPro)
        }
    }
}

private class FakeSubscriptionRepo(
    private var initialPro: Boolean = false
) : SubscriptionRepository {
    private val plans = listOf(
        SubscriptionPlan("1", "Pro Monthly", "฿99", "เดือน", "Desc", emptyList(), true)
    )

    override suspend fun getSubscriptionPlans(): Result<List<SubscriptionPlan>> = Result.success(plans)

    override suspend fun purchaseSubscription(planId: String, paymentResult: PaymentResult): Result<Boolean> {
        return if (paymentResult == PaymentResult.SUCCESS) {
            initialPro = true
            Result.success(true)
        } else {
            Result.failure(RuntimeException("Error"))
        }
    }

    override suspend fun cancelSubscription(): Result<Boolean> {
        initialPro = false
        Result.success(true)
        return Result.success(true)
    }

    override suspend fun isUserPro(): Result<Boolean> = Result.success(initialPro)
}
