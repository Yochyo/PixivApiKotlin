package de.yochyo.pixiv_api.response_types

data class PixivBookmarkSearch(
    val bookmarkTags: List<PixivTag>,
    val nextUrl: String?
)