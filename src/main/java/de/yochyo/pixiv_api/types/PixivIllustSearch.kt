package de.yochyo.pixiv_api.types


data class PixivIllustSearch(//TODO as iterable
    val illusts: List<PixivIllust>,
    val nextUrl: String?,
    val searchSpanLimit: Int?
)
