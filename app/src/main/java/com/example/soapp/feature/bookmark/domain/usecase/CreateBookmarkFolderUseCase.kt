package com.example.soapp.feature.bookmark.domain.usecase

import com.example.soapp.core.model.BookmarkFolder
import com.example.soapp.feature.bookmark.domain.repository.BookmarkFolderRepository

class CreateBookmarkFolderUseCase(
    private val repository: BookmarkFolderRepository
) {
    suspend operator fun invoke(name: String): Result<BookmarkFolder> {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) {
            return Result.failure(IllegalArgumentException("ชื่อโฟลเดอร์ต้องไม่เป็นค่าว่าง"))
        }
        return repository.createFolder(trimmed)
    }
}
