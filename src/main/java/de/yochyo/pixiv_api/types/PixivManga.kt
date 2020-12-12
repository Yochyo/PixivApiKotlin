package de.yochyo.pixiv_api.types

data class PixivManga(
    val id: Int,
    val title: String,
    val type: String,
    val imageUrls: PixivIllustImageUrls,
    val caption: String,
    val restrict: Int,
    val user: PixivUser,
    val tags: List<PixivTag>,
    val tools: List<String>,
    val createDate: String,
    val pageCount: String,
    val width: Int,
    val height: Int,
    val sanityLevel: Int,
    val xRestrict: Int,
    val series: PixivSeries?,
    val metaSinglePage: Any, //TODO,
    val metaPages: List<PixivMetaPage>,
    val totalView: Int,
    val totalBookmarks: Int,
    val isBookmarked: Boolean,
    val visible: Boolean,
    val isMuted: Boolean
)
