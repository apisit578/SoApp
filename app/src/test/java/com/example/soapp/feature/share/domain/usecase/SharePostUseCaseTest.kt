package com.example.soapp.feature.share.domain.usecase

import com.example.soapp.core.model.Post
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SharePostUseCaseTest {

    @Test
    fun `invoke should generate valid deep link and share payload`() {
        val post = Post("post_123", "Alice", "@alice", null, "Clean Architecture Test", "1m", 0)
        val useCase = SharePostUseCase()

        val payload = useCase(post)

        assertEquals("https://soapp.app/post/post_123", payload.deepLinkUrl)
        assertTrue(payload.shareText.contains("https://soapp.app/post/post_123"))
        assertTrue(payload.shareText.contains("Alice"))
    }
}
