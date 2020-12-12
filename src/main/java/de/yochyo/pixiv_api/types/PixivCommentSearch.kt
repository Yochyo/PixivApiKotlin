package de.yochyo.pixiv_api.types

data class PixivCommentSearch(
    val totalComments: Int,
    val comments: List<PixivComment>,
    val nextUrl: String?
)
