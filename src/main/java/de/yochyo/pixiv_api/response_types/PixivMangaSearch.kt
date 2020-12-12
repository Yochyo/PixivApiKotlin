package de.yochyo.pixiv_api.response_types

data class PixivMangaSearch(
    val illusts: List<PixivManga>,
    val rankingIllusts: Any?, //List<PixivManga> | [],
    val privacyPolicy: Any, //TODO,
    val nextUrl: String?
)
