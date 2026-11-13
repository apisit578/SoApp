package com.example.soapp.feature.blockuser.domain.repository

interface BlockRepository {
    suspend fun blockUser(userId: String): Result<Boolean>
    suspend fun unblockUser(userId: String): Result<Boolean>
    suspend fun getBlockedUserIds(): Result<List<String>>
}
