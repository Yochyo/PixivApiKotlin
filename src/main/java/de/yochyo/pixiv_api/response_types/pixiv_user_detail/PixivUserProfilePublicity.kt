package de.yochyo.pixiv_api.response_types.pixiv_user_detail

data class PixivUserProfilePublicity(
    val gender: String,
    val region: String,
    val birthDay: String,
    val birthYear: String,
    val job: String,
    val pawoo: Boolean
)
