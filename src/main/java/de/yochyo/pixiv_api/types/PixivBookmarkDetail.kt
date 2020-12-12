package de.yochyo.pixiv_api.types


internal data class PixivBookmarkDetailWrapper(val bookmarkDetail: PixivBookmarkDetail)
data class PixivBookmarkDetail(
    val isBookmarked: Boolean,
    val tags: List<PixivTag>,
    val restrict: String
)
