package de.yochyo.pixiv_api.response_types

data class PixivUserSearchPreview(
    val user: PixivUser,
    val illusts: List<PixivIllust>,
    val novels: List<PixivNovel>,
    val isMuted: Boolean
)
