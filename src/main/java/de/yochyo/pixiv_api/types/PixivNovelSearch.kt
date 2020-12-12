package de.yochyo.pixiv_api.types

data class PixivNovelSearch(
    val novels: List<PixivNovel>,
    val nextUrl: String?,
    val privacyPolicy: Any?,//TODO searchNovels()
    val searchSpanLimit: Int?
)
