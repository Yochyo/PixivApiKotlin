package de.yochyo.pixiv_api.types

data class PixivUserSearch(
    val userPreviews: List<PixivUserSearchPreview>,
    val nextUrl: String?
)