package com.example.soapp.feature.share.domain.usecase

import com.example.soapp.core.model.Post

data class SharePayload(
    val title: String,
    val shareText: String,
    val deepLinkUrl: String
)

class SharePostUseCase {

    operator fun invoke(post: Post): SharePayload {
        val deepLinkUrl = "https://soapp.app/post/${post.id}"
        val shareText = "อ่านโพสต์ของ ${post.authorName} (${post.authorHandle}) บน SoApp:\n\"${post.content.take(60)}...\"\n\nดูเพิ่มเติมที่: $deepLinkUrl"
        return SharePayload(
            title = "แชร์โพสต์ของ ${post.authorName}",
            shareText = shareText,
            deepLinkUrl = deepLinkUrl
        )
    }
}
