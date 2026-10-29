package com.example.soapp.feature.profile.domain.usecase

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.UserProfile
import com.example.soapp.feature.profile.domain.repository.ProfileRepository

data class UserProfileData(
    val profile: UserProfile,
    val posts: List<Post>
)

class GetUserProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<UserProfileData> {
        return runCatching {
            val profile = repository.getUserProfile().getOrThrow()
            val posts = repository.getUserPosts().getOrThrow()
            UserProfileData(profile = profile, posts = posts)
        }
    }
}
