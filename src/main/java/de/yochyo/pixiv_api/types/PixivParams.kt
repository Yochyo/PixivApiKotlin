package de.yochyo.pixiv_api.types

data class PixivParams(
    val userId: Int? = null,
    val type: String? = null,
    val filter: String? = null,
    val restrict: String? = null, //public, private
    val illustId: Int? = null,
    val contentType: String? = null,
    val includeTotalComments: Boolean? = null,
    val includeRankingLabel: Boolean? = null,
    val includeRankingIllusts: Boolean? = null,
    val includeRankingNovels: Boolean? = null,
    val mode: Mode? = null,
    val word: String? = null,
    val searchTarget: String? = null, //partial_match_for_tags, exact_match_for_tags, title_and_caption
    val sort: String? = null, //'date_desc' | 'date_asc' | 'popular_desc'
    val startDate: String? = null,
    val endDate: String? = null,
    val offset: Int? = null
)
