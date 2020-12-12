package de.yochyo.pixiv_api.types

data class PixivBookmarkSearch(
    val bookmarkTags: List<PixivTag>,
    val nextUrl: String?
)