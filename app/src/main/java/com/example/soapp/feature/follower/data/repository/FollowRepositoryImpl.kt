package com.example.soapp.feature.follower.data.repository

import com.example.soapp.core.model.FollowUser
import com.example.soapp.feature.follower.data.datasource.MockFollowDataSource
import com.example.soapp.feature.follower.domain.repository.FollowRepository

class FollowRepositoryImpl(
    private val dataSource: MockFollowDataSource
) : FollowRepository {

    override suspend fun getFollowers(userId: String): Result<List<FollowUser>> {
        return runCatching { dataSource.fetchFollowers(userId) }
    }

    override suspend fun getFollowing(userId: String): Result<List<FollowUser>> {
        return runCatching { dataSource.fetchFollowing(userId) }
    }

    override suspend fun toggleFollow(targetUserId: String): Result<FollowUser> {
        return runCatching { dataSource.toggleFollow(targetUserId) }
    }
}
