package de.yochyo.pixiv_api.response_types


internal data class PixivBookmarkDetailWrapper(val bookmarkDetail: PixivBookmarkDetail)
data class PixivBookmarkDetail(
    val isBookmarked: Boolean,
    val tags: List<PixivTag>,
    val restrict: String
)
