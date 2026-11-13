package com.example.soapp.feature.comment.domain.usecase

import com.example.soapp.core.model.Comment
import com.example.soapp.feature.comment.domain.repository.CommentRepository

class AddCommentUseCase(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(postId: String, text: String): Result<Comment> {
        if (text.isBlank()) {
            return Result.failure(IllegalArgumentException("ความคิดเห็นต้องไม่เป็นค่าว่าง"))
        }
        return repository.addComment(postId, text)
    }
}
