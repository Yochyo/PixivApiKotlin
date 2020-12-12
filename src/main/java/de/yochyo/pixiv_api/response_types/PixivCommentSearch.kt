package de.yochyo.pixiv_api.response_types

data class PixivCommentSearch(
    val totalComments: Int,
    val comments: List<PixivComment>,
    val nextUrl: String?
)
