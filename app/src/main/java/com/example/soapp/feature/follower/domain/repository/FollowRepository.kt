package com.example.soapp.feature.follower.domain.repository

import com.example.soapp.core.model.FollowUser

interface FollowRepository {
    suspend fun getFollowers(userId: String): Result<List<FollowUser>>
    suspend fun getFollowing(userId: String): Result<List<FollowUser>>
    suspend fun toggleFollow(targetUserId: String): Result<FollowUser>
}
