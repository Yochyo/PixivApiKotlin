package de.yochyo.pixiv_api.response_types

data class PixivComment(
    val id: Int,
    val comment: String,
    val date: String,
    val user: PixivUser,
    val parentComment: Any//PixivComment | {}
)
