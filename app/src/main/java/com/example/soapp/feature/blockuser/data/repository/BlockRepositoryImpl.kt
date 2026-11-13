package com.example.soapp.feature.blockuser.data.repository

import com.example.soapp.feature.blockuser.data.datasource.MockBlockDataSource
import com.example.soapp.feature.blockuser.domain.repository.BlockRepository

class BlockRepositoryImpl(
    private val dataSource: MockBlockDataSource
) : BlockRepository {

    override suspend fun blockUser(userId: String): Result<Boolean> {
        return runCatching { dataSource.blockUser(userId) }
    }

    override suspend fun unblockUser(userId: String): Result<Boolean> {
        return runCatching { dataSource.unblockUser(userId) }
    }

    override suspend fun getBlockedUserIds(): Result<List<String>> {
        return runCatching { dataSource.fetchBlockedUserIds() }
    }
}
