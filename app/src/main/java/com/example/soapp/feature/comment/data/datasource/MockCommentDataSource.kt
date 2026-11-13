package com.example.soapp.feature.comment.data.datasource

import com.example.soapp.core.model.Comment
import kotlinx.coroutines.delay

interface MockCommentDataSource {
    suspend fun fetchComments(postId: String): List<Comment>
    suspend fun addComment(postId: String, text: String): Comment
}

class MockCommentDataSourceImpl : MockCommentDataSource {

    private val commentsMap = mutableMapOf<String, MutableList<Comment>>(
        "1" to mutableListOf(
            Comment("c1", "1", "Dev Thailand", "@dev_th", null, "โครงสร้าง Clean Architecture เรียบร้อยดีมากครับ!", "1ชม."),
            Comment("c2", "1", "Kotlin Lover", "@kotlin_l", null, "ใช้ StateFlow + Compose ทำงานไวสุดๆ 👍", "30นาที")
        ),
        "2" to mutableListOf(
            Comment("c3", "2", "Somchai Dev", "@somchai_dev", null, "เห็นด้วยเลยครับ อ่านง่ายและเขียน Unit Test สะดวกมาก", "2ชม.")
        )
    )

    override suspend fun fetchComments(postId: String): List<Comment> {
        delay(300)
        return commentsMap[postId]?.toList() ?: emptyList()
    }

    override suspend fun addComment(postId: String, text: String): Comment {
        delay(300)
        val list = commentsMap.getOrPut(postId) { mutableListOf() }
        val newComment = Comment(
            id = "c_${System.currentTimeMillis()}",
            postId = postId,
            authorName = "Somchai Dev",
            authorHandle = "@somchai_dev",
            authorAvatarUrl = null,
            text = text,
            timeAgo = "เมื่อครู่"
        )
        list.add(0, newComment)
        return newComment
    }
}
