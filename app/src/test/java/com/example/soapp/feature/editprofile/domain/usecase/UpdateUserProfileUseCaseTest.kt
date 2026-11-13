package com.example.soapp.feature.editprofile.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.UserProfile
import com.example.soapp.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateUserProfileUseCaseTest {

    @Test
    fun `invoke with blank name should return failure`() = runTest {
        val fakeRepo = FakeProfileRepo()
        val useCase = UpdateUserProfileUseCase(fakeRepo)

        val result = useCase(name = "   ", bio = "Developer")

        assertTrue(result.isFailure)
        assertEquals("ชื่อโปรไฟล์ต้องไม่เป็นค่าว่าง", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke with valid data should update profile successfully`() = runTest {
        val fakeRepo = FakeProfileRepo()
        val useCase = UpdateUserProfileUseCase(fakeRepo)

        val result = useCase(name = "Somchai New", bio = "New Bio")

        assertTrue(result.isSuccess)
        assertEquals("Somchai New", result.getOrNull()?.name)
    }
}

private class FakeProfileRepo : ProfileRepository {
    override suspend fun getUserProfile(): Result<UserProfile> = Result.success(UserProfile("u1", "Somchai", "@somchai", "Bio"))
    override suspend fun getUserPosts(): Result<List<Post>> = Result.success(emptyList())
    override suspend fun updateProfile(name: String, bio: String): Result<UserProfile> {
        return Result.success(UserProfile("u1", name, "@somchai", bio))
    }
}
