package com.example.soapp.feature.bookmark.domain.repository

import com.example.soapp.core.model.BookmarkFolder

interface BookmarkFolderRepository {
    suspend fun createFolder(name: String): Result<BookmarkFolder>
    suspend fun getFolders(): Result<List<BookmarkFolder>>
}
