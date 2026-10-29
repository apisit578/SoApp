package com.example.soapp.core.model

/**
 * Domain model representing a Subscription Plan option.
 */
data class SubscriptionPlan(
    val id: String,
    val name: String,
    val price: String,
    val billingPeriod: String,
    val description: String,
    val features: List<String>,
    val isBestValue: Boolean = false
)
