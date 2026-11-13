package com.example.soapp.feature.bookmark.domain.usecase

import com.example.soapp.core.model.BookmarkFolder
import com.example.soapp.feature.bookmark.domain.repository.BookmarkFolderRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CreateBookmarkFolderUseCaseTest {

    @Test
    fun `invoke with blank name should return failure`() = runTest {
        val fakeRepo = FakeFolderRepo()
        val useCase = CreateBookmarkFolderUseCase(fakeRepo)

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertEquals("ชื่อโฟลเดอร์ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid name should create folder`() = runTest {
        val fakeRepo = FakeFolderRepo()
        val useCase = CreateBookmarkFolderUseCase(fakeRepo)

        val result = useCase("ไอเดียดีๆ")

        assertTrue(result.isSuccess)
        assertEquals("ไอเดียดีๆ", result.getOrNull()?.name)
    }
}

private class FakeFolderRepo : BookmarkFolderRepository {
    private val folders = mutableListOf<BookmarkFolder>()

    override suspend fun createFolder(name: String): Result<BookmarkFolder> {
        val f = BookmarkFolder("f1", name, 0)
        folders.add(f)
        return Result.success(f)
    }

    override suspend fun getFolders(): Result<List<BookmarkFolder>> = Result.success(folders)
}
