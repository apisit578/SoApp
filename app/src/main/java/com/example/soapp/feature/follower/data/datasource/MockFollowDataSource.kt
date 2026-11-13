package com.example.soapp.feature.follower.data.datasource

import com.example.soapp.core.model.FollowUser
import kotlinx.coroutines.delay

interface MockFollowDataSource {
    suspend fun fetchFollowers(userId: String): List<FollowUser>
    suspend fun fetchFollowing(userId: String): List<FollowUser>
    suspend fun toggleFollow(targetUserId: String): FollowUser
}

class MockFollowDataSourceImpl : MockFollowDataSource {

    private val followersList = mutableListOf(
        FollowUser("u1", "Android Thailand", "@android_th", "Official Android Community", null, true),
        FollowUser("u2", "Kotlin Lover", "@kotlin_l", "JVM & Kotlin Passionate", null, false),
        FollowUser("u3", "Dev Thailand", "@dev_th", "Software Developer Network", null, true)
    )

    private val followingList = mutableListOf(
        FollowUser("u1", "Android Thailand", "@android_th", "Official Android Community", null, true),
        FollowUser("u3", "Dev Thailand", "@dev_th", "Software Developer Network", null, true)
    )

    override suspend fun fetchFollowers(userId: String): List<FollowUser> {
        delay(300)
        return followersList.toList()
    }

    override suspend fun fetchFollowing(userId: String): List<FollowUser> {
        delay(300)
        return followingList.toList()
    }

    override suspend fun toggleFollow(targetUserId: String): FollowUser {
        delay(200)
        val index = followersList.indexOfFirst { it.id == targetUserId }
        if (index != -1) {
            val old = followersList[index]
            val updated = old.copy(isFollowing = !old.isFollowing)
            followersList[index] = updated
            return updated
        } else {
            val updated = FollowUser(targetUserId, "User $targetUserId", "@user_$targetUserId", "User Bio", null, true)
            followersList.add(updated)
            return updated
        }
    }
}
