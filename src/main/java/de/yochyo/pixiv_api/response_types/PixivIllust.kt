package de.yochyo.pixiv_api.response_types


data class PixivIllust(
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
    val pageCount: Int,
    val width: Int,
    val height: Int,
    val sanityLevel: Int,
    val metaSinglePage: PixivIllustMetaSinglePage,
    val metaPages: List<PixivMetaPage>,
    val totalView: Int,
    val totalBookmarks: Int,
    val isBookmarked: Boolean,
    val visible: Boolean,
    val isMuted: Boolean,
    val totalComments: Int
)