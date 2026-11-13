package com.example.soapp.feature.blockuser.domain.usecase

import com.example.soapp.feature.blockuser.domain.repository.BlockRepository

class GetBlockedUsersUseCase(
    private val repository: BlockRepository
) {
    suspend operator fun invoke(): Result<List<String>> {
        return repository.getBlockedUserIds()
    }
}
