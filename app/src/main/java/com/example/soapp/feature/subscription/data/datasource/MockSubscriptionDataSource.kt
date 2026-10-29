package com.example.soapp.feature.subscription.data.datasource

import com.example.soapp.core.model.SubscriptionPlan
import kotlinx.coroutines.delay

interface MockSubscriptionDataSource {
    suspend fun getPlans(): List<SubscriptionPlan>
    suspend fun purchase(planId: String): Boolean
    fun isProUser(): Boolean
}

class MockSubscriptionDataSourceImpl : MockSubscriptionDataSource {

    private var isPro: Boolean = false

    private val plans = listOf(
        SubscriptionPlan(
            id = "soapp_pro_monthly",
            name = "SoApp Pro รายเดือน",
            price = "฿99",
            billingPeriod = "เดือน",
            description = "เหมาะสำหรับทดลองใช้งานสิทธิพิเศษเต็มรูปแบบ",
            features = listOf(
                "ตราสัญลักษณ์ Pro สีทองบนโปรไฟล์",
                "ไม่มีโฆษณาคั่นในหน้า Feed",
                "เข้าถึงฟีเจอร์ใหม่ก่อนใคร",
                "ซัพพอร์ตระดับ VIP"
            ),
            isBestValue = false
        ),
        SubscriptionPlan(
            id = "soapp_pro_yearly",
            name = "SoApp Pro รายปี",
            price = "฿890",
            billingPeriod = "ปี",
            description = "ประหยัดกว่า 25% ชำระครั้งเดียวคุ้มค่าตลอดปี",
            features = listOf(
                "สิทธิประโยชน์ทั้งหมดของ Pro รายเดือน",
                "ส่วนลดพิเศษ 25% เทียบกับรายเดือน",
                "สิทธิ์ร่วมกิจกรรมพิเศษสำหรับสมาชิกรายปี"
            ),
            isBestValue = true
        )
    )

    override suspend fun getPlans(): List<SubscriptionPlan> {
        delay(300)
        return plans
    }

    override suspend fun purchase(planId: String): Boolean {
        delay(1200) // Simulate Billing sheet & payment processing
        isPro = true
        return true
    }

    override fun isProUser(): Boolean = isPro
}
