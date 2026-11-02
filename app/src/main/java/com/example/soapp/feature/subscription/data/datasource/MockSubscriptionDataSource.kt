package com.example.soapp.feature.subscription.data.datasource

import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan
import com.example.soapp.core.session.UserSessionManager
import kotlinx.coroutines.delay

interface MockSubscriptionDataSource {
    suspend fun getPlans(): List<SubscriptionPlan>
    suspend fun purchase(planId: String, paymentResult: PaymentResult): Boolean
    suspend fun cancelSubscription(): Boolean
    fun isProUser(): Boolean
}

class MockSubscriptionDataSourceImpl : MockSubscriptionDataSource {

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

    override suspend fun purchase(planId: String, paymentResult: PaymentResult): Boolean {
        when (paymentResult) {
            PaymentResult.SUCCESS -> {
                delay(1000)
                UserSessionManager.setProStatus(true)
                return true
            }
            PaymentResult.DECLINED -> {
                delay(800)
                throw IllegalStateException("การชำระเงินล้มเหลว: วงเงินในบัตรไม่พอหรือบัตรโดนปฏิเสธ (Sandbox Declined)")
            }
            PaymentResult.CANCELLED -> {
                delay(300)
                return false
            }
        }
    }

    override suspend fun cancelSubscription(): Boolean {
        delay(600)
        UserSessionManager.setProStatus(false)
        return true
    }

    override fun isProUser(): Boolean = UserSessionManager.isPro.value
}
