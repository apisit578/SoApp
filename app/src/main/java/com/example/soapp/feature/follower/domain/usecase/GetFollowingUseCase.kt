package com.example.soapp.feature.follower.domain.usecase

import com.example.soapp.core.model.FollowUser
import com.example.soapp.feature.follower.domain.repository.FollowRepository

class GetFollowingUseCase(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(userId: String): Result<List<FollowUser>> {
        return repository.getFollowing(userId)
    }
}
