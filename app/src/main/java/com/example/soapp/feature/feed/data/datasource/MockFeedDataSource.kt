package com.example.soapp.feature.feed.data.datasource

import com.example.soapp.core.model.Post
import kotlinx.coroutines.delay

interface MockFeedDataSource {
    suspend fun fetchPosts(): List<Post>
    suspend fun toggleLike(postId: String): Post
}

class MockFeedDataSourceImpl : MockFeedDataSource {

    private val posts = mutableListOf(
        Post(
            id = "1",
            authorName = "Somchai Dev",
            authorHandle = "@somchai_dev",
            content = "ยินดีต้อนรับสู่ SoApp! แอพนี้สร้างด้วย MVVM + Clean Architecture บน Jetpack Compose 🚀",
            timeAgo = "2ชม.",
            likeCount = 12,
            isLiked = false,
            commentCount = 3
        ),
        Post(
            id = "2",
            authorName = "Android Thailand",
            authorHandle = "@android_th",
            content = "Modern Android Development ในปี 2026 เน้น Unidirectional Data Flow, Compose, และ Clean Architecture เพื่อความง่ายในการขยายระบบ 📱✨",
            timeAgo = "5ชม.",
            likeCount = 45,
            isLiked = true,
            commentCount = 8
        ),
        Post(
            id = "3",
            authorName = "Kotlin Fan",
            authorHandle = "@kotlin_fan",
            content = "StateFlow + ViewModel + Compose = ❤️ ทำงานร่วมกันได้อย่างราบรื่นมาก!",
            timeAgo = "1วัน",
            likeCount = 99,
            isLiked = false,
            commentCount = 15
        )
    )

    override suspend fun fetchPosts(): List<Post> {
        delay(600) // Simulate network delay
        return posts.toList()
    }

    override suspend fun toggleLike(postId: String): Post {
        delay(200) // Simulate network delay
        val index = posts.indexOfFirst { it.id == postId }
        if (index != -1) {
            val current = posts[index]
            val updated = current.copy(
                isLiked = !current.isLiked,
                likeCount = if (current.isLiked) current.likeCount - 1 else current.likeCount + 1
            )
            posts[index] = updated
            return updated
        } else {
            throw NoSuchElementException("Post with id $postId not found")
        }
    }
}
