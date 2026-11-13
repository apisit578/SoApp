package com.example.soapp.feature.bookmark.data.datasource

import com.example.soapp.core.model.BookmarkFolder
import kotlinx.coroutines.delay

interface MockBookmarkFolderDataSource {
    suspend fun createFolder(name: String): BookmarkFolder
    suspend fun fetchFolders(): List<BookmarkFolder>
}

class MockBookmarkFolderDataSourceImpl : MockBookmarkFolderDataSource {

    private val folders = mutableListOf(
        BookmarkFolder("f1", "อ่านภายหลัง", 2),
        BookmarkFolder("f2", "ไอเดียโค้ด", 1)
    )

    override suspend fun createFolder(name: String): BookmarkFolder {
        delay(200)
        val newFolder = BookmarkFolder(
            id = "f_${System.currentTimeMillis()}",
            name = name,
            postCount = 0
        )
        folders.add(newFolder)
        return newFolder
    }

    override suspend fun fetchFolders(): List<BookmarkFolder> {
        delay(200)
        return folders.toList()
    }
}
