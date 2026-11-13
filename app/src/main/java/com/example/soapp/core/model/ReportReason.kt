package com.example.soapp.core.model

enum class ReportReason(val title: String) {
    SPAM("สแปม หรือ ข้อความหลอกลวง (Spam)"),
    INAPPROPRIATE("เนื้อหาไม่เหมาะสม หรือ ไม่เหมาะสมแก่เยาวชน"),
    HARASSMENT("การคุกคาม หรือ กลั่นแกล้ง (Harassment)")
}
