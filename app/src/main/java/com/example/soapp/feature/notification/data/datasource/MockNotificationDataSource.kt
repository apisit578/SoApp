package com.example.soapp.feature.notification.data.datasource

import com.example.soapp.core.model.NotificationItem
import com.example.soapp.core.model.NotificationType
import kotlinx.coroutines.delay

interface MockNotificationDataSource {
    suspend fun fetchNotifications(): List<NotificationItem>
    suspend fun markAsRead(id: String)
}

class MockNotificationDataSourceImpl : MockNotificationDataSource {

    private val notifications = mutableListOf(
        NotificationItem("n1", NotificationType.LIKE, "Dev Thailand", "กดถูกใจโพสต์ของคุณ", "10นาที", false),
        NotificationItem("n2", NotificationType.COMMENT, "Kotlin Lover", "แสดงความคิดเห็นบนโพสต์ของคุณ", "1ชม.", false),
        NotificationItem("n3", NotificationType.SYSTEM, "ระบบ SoApp Pro", "ยินดีต้อนรับสู่สมาชิกระดับ Pro!", "1วัน", true)
    )

    override suspend fun fetchNotifications(): List<NotificationItem> {
        delay(300)
        return notifications.toList()
    }

    override suspend fun markAsRead(id: String) {
        delay(100)
        val index = notifications.indexOfFirst { it.id == id }
        if (index != -1) {
            notifications[index] = notifications[index].copy(isRead = true)
        }
    }
}
