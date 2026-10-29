package com.example.soapp.feature.profile.data.datasource

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.UserProfile
import kotlinx.coroutines.delay

interface MockProfileDataSource {
    suspend fun fetchProfile(): UserProfile
    suspend fun fetchUserPosts(): List<Post>
}

class MockProfileDataSourceImpl : MockProfileDataSource {

    private val profile = UserProfile(
        id = "user_1",
        name = "Somchai Dev",
        handle = "@somchai_dev",
        bio = "Android Developer | Kotlin & Jetpack Compose Enthusiast 🚀 | Building awesome mobile apps",
        avatarUrl = null,
        postCount = 12,
        followerCount = 1250,
        followingCount = 380
    )

    private val userPosts = listOf(
        Post(
            id = "p1",
            authorName = "Somchai Dev",
            authorHandle = "@somchai_dev",
            content = "เพิ่งปล่อยอัปเดต SoApp เวอร์ชั่นใหม่! ใช้ Jetpack Compose ทั้งหมดครับ 📱💻",
            timeAgo = "3วัน",
            likeCount = 34,
            isLiked = true,
            commentCount = 5
        ),
        Post(
            id = "p2",
            authorName = "Somchai Dev",
            authorHandle = "@somchai_dev",
            content = "กำลังศึกษาเรื่อง Clean Architecture ใน Android 2026 รู้สึกว่าโครงสร้างโค้ดอ่านง่ายขึ้นเยอะมากครับ 🔥",
            timeAgo = "1สัปดาห์",
            likeCount = 89,
            isLiked = false,
            commentCount = 12
        )
    )

    override suspend fun fetchProfile(): UserProfile {
        delay(400)
        return profile
    }

    override suspend fun fetchUserPosts(): List<Post> {
        delay(400)
        return userPosts
    }
}
