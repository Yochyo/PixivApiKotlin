package de.yochyo.pixiv_api.response_types.pixiv_user_detail

data class PixivUserProfile(
    val webpage: String?,
    val gender: String, //male, female, unknown

    val birth: String,
    val birthday: String?,
    val birthYear: String,
    val region: String,
    val addressId: Int,
    val countryCode: String,
    val job: String,
    val jobId: Int,
    val totalFollowUsers: Int,
    val totalMypixivUsers: Int,
    val totalIllusts: Int,
    val totalManga: Int,
    val totalNovels: Int,
    val totalIllustBookmarksPublic: Int,
    val totalIllustSeries: Int,
    val backgroundImageUrl: String?,
    val twitterAccount: String,
    val twitterUrl: String?,
    val pawooUrl: String?,
    val isPremium: Boolean,
    val isUsingCustomProfileImage: Boolean
)
