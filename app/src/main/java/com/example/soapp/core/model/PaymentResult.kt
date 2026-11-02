package com.example.soapp.core.model

/**
 * Payment result options for Sandbox Testing.
 */
enum class PaymentResult(
    val title: String,
    val description: String
) {
    SUCCESS(
        title = "🟢 บัตรทดสอบ: ชำระเงินสำเร็จ",
        description = "จำลองการตัดเงินผ่านบัตร成功 และมอบสิทธิ์ SoApp Pro ทันที"
    ),
    DECLINED(
        title = "🔴 บัตรทดสอบ: วงเงินไม่พอ / บัตรโดนปฏิเสธ",
        description = "จำลองระบบตัดเงินล้มเหลว แสดงข้อความแจ้งเตือน Error"
    ),
    CANCELLED(
        title = "🟡 ผู้ใช้กดยกเลิกการชำระเงิน",
        description = "จำลองผู้ใช้กดปิดหน้าต่างชำระเงินของ Google Play"
    )
}
