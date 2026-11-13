package com.example.soapp.feature.blockuser.domain.usecase

import com.example.soapp.feature.blockuser.domain.repository.BlockRepository

class BlockUserUseCase(
    private val repository: BlockRepository
) {
    suspend operator fun invoke(userId: String): Result<Boolean> {
        if (userId.isBlank()) {
            return Result.failure(IllegalArgumentException("User ID ต้องไม่เป็นค่าว่าง"))
        }
        return repository.blockUser(userId)
    }
}
