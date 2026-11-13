package com.example.soapp.feature.editprofile.domain.usecase

import com.example.soapp.core.model.UserProfile
import com.example.soapp.feature.profile.domain.repository.ProfileRepository

class UpdateUserProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(name: String, bio: String): Result<UserProfile> {
        val trimmedName = name.trim()
        val trimmedBio = bio.trim()

        if (trimmedName.isEmpty()) {
            return Result.failure(IllegalArgumentException("ชื่อโปรไฟล์ต้องไม่เป็นค่าว่าง"))
        }
        return repository.updateProfile(trimmedName, trimmedBio)
    }
}
