package com.example.soapp.feature.comment.data.repository

import com.example.soapp.core.model.Comment
import com.example.soapp.feature.comment.data.datasource.MockCommentDataSource
import com.example.soapp.feature.comment.domain.repository.CommentRepository

class CommentRepositoryImpl(
    private val dataSource: MockCommentDataSource
) : CommentRepository {

    override suspend fun getComments(postId: String): Result<List<Comment>> {
        return runCatching { dataSource.fetchComments(postId) }
    }

    override suspend fun addComment(postId: String, text: String): Result<Comment> {
        return runCatching { dataSource.addComment(postId, text) }
    }
}
