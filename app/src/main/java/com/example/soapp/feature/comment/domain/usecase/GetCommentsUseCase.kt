package com.example.soapp.feature.comment.domain.usecase

import com.example.soapp.core.model.Comment
import com.example.soapp.feature.comment.domain.repository.CommentRepository

class GetCommentsUseCase(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(postId: String): Result<List<Comment>> {
        return repository.getComments(postId)
    }
}
