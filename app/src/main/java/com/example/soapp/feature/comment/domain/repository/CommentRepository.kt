package com.example.soapp.feature.comment.domain.repository

import com.example.soapp.core.model.Comment

interface CommentRepository {
    suspend fun getComments(postId: String): Result<List<Comment>>
    suspend fun addComment(postId: String, text: String): Result<Comment>
}
