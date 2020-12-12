package de.yochyo.pixiv_api.response_types

data class PixivNovel(
    val id: Int,
    val title: String,
    val caption: String,
    val restrict: Int,
    val imageUrls: PixivIllustImageUrls,
    val createDate: String,
    val tags: List<PixivTag>,
    val pageCount: Int,
    val textLength: Int,
    val user: PixivUser,
    val series: Any,//TODO,
    val isBookmarked: Boolean,
    val totalBookmarks: Int,
    val totalView: Int,
    val visible: Boolean,
    val totalComments: Int,
    val isMuted: Boolean,
    val isMypixivOnly: Boolean,
    val isXRestricted: Boolean
)
