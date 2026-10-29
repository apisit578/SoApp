package com.example.soapp.feature.profile.data.repository

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.UserProfile
import com.example.soapp.feature.profile.data.datasource.MockProfileDataSource
import com.example.soapp.feature.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val dataSource: MockProfileDataSource
) : ProfileRepository {

    override suspend fun getUserProfile(): Result<UserProfile> {
        return runCatching { dataSource.fetchProfile() }
    }

    override suspend fun getUserPosts(): Result<List<Post>> {
        return runCatching { dataSource.fetchUserPosts() }
    }
}
