package com.example.soapp.feature.follower.domain.usecase

import com.example.soapp.core.model.FollowUser
import com.example.soapp.feature.follower.domain.repository.FollowRepository

class ToggleFollowUseCase(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(targetUserId: String): Result<FollowUser> {
        return repository.toggleFollow(targetUserId)
    }
}
