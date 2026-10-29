package com.example.soapp.feature.profile.domain.repository

import com.example.soapp.core.model.Post
import com.example.soapp.core.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun getUserPosts(): Result<List<Post>>
}
