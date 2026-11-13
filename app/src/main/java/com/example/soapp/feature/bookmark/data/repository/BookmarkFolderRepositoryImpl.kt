package com.example.soapp.feature.bookmark.data.repository

import com.example.soapp.core.model.BookmarkFolder
import com.example.soapp.feature.bookmark.data.datasource.MockBookmarkFolderDataSource
import com.example.soapp.feature.bookmark.domain.repository.BookmarkFolderRepository

class BookmarkFolderRepositoryImpl(
    private val dataSource: MockBookmarkFolderDataSource
) : BookmarkFolderRepository {

    override suspend fun createFolder(name: String): Result<BookmarkFolder> {
        return runCatching { dataSource.createFolder(name) }
    }

    override suspend fun getFolders(): Result<List<BookmarkFolder>> {
        return runCatching { dataSource.fetchFolders() }
    }
}
