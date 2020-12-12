package de.yochyo.pixiv_api.response_types

data class PixivUserSearch(
    val userPreviews: List<PixivUserSearchPreview>,
    val nextUrl: String?
)