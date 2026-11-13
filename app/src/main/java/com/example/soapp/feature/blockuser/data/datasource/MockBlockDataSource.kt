package com.example.soapp.feature.blockuser.data.datasource

import kotlinx.coroutines.delay

interface MockBlockDataSource {
    suspend fun blockUser(userId: String): Boolean
    suspend fun unblockUser(userId: String): Boolean
    suspend fun fetchBlockedUserIds(): List<String>
}

class MockBlockDataSourceImpl : MockBlockDataSource {

    private val blockedUserIds = mutableListOf<String>()

    override suspend fun blockUser(userId: String): Boolean {
        delay(200)
        if (!blockedUserIds.contains(userId)) {
            blockedUserIds.add(userId)
        }
        return true
    }

    override suspend fun unblockUser(userId: String): Boolean {
        delay(200)
        blockedUserIds.remove(userId)
        return true
    }

    override suspend fun fetchBlockedUserIds(): List<String> {
        delay(200)
        return blockedUserIds.toList()
    }
}
